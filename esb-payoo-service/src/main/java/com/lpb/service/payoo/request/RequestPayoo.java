package com.lpb.service.payoo.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.service.payoo.config.ServiceConfig;
import com.lpb.service.payoo.model.*;
import com.lpb.service.payoo.service.PayooPartnerService;
import com.lpb.service.payoo.service.RequestPayooService;
import com.lpb.service.payoo.utils.*;
import com.lpb.service.payoo.utils.ResponseCodeESB;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Log4j2
public class RequestPayoo implements RequestPayooService {
    private static final Logger logger = LogManager.getLogger(RequestPayoo.class);
    @Autowired
    ServiceConfig serviceConfig;
    private final String SUSCESS = "0";

    @Override
    public ESBResponse queryBillBE(ESBRequest req) {
        ESBResponse payooResponse = Map.mapResponse(req);
        DataRes respEntity = null;

        String responseCode = null;
        String msg = null;
        String responseContent = null;
        String operation = "QueryBillBE";
        ESBResponse.RESCODE rescode = new ESBResponse.RESCODE();
        boolean checkError = false;
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("<--- resquest payooRequest: " + req.toString());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String requestTime = sdf.format(new Date());
            logger.info("<--- CertPrefix: " + (serviceConfig.getCertPrefix() + serviceConfig.getCertPfx()));

            QueryBillExReq.Data body = new QueryBillExReq.Data(req.getHeader().getUserId(),req.getBody().getService().getRequestAccount(),req.getBody().getService().getServiceId(), req.getBody().getService().getProductCode(), req.getHeader().getCorrelId(), req.getHeader().getAction());
            String signature = RSAUtil.sign(body.toString(), serviceConfig.getCertPrefix() + serviceConfig.getCertPfx(), serviceConfig.getCertPass());
            logger.info("<--- signature1: " + signature);

            QueryBillExReq queryBillExReq = new QueryBillExReq(body.toString());
            logger.info("<--- resquest QueryBillExReq: " + queryBillExReq.toString());

            PayooPartnerService service = PayooPartnerService.getInstance(null,
                req.getHeader().getDestination(),
                serviceConfig.getConnectTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getPartnerCode(),
                operation, signature,
                requestTime
            );
//            DEFAULT
            rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());

            Call<DataRes> call = service.QueryBillBE(queryBillExReq);
            Response<DataRes> response = call.execute();
            logger.info("<--- response: " + response);
            if (response.body() != null) {
                respEntity = response.body();
                logger.info("<--- respEntity: " + respEntity.toString());
                QueryBillExRes queryBillExRes = mapper.readValue(respEntity.getData(), QueryBillExRes.class);
                logger.info("<--- queryBillExRes: " + queryBillExRes.toString());

                responseCode = String.valueOf(queryBillExRes.getReturnCode());
                msg = queryBillExRes.getDescriptionCode();
                responseContent = respEntity.toString();
                logger.info("<--- respBody: " + responseContent);
                logger.info("<--- responseCode: " + responseCode);

                if (responseCode != null) {
                    if (responseCode.equals(ResponseCodeQueryBillEx.SUCCESSFUL.getResponseCodePayoo())) {
                        if (queryBillExRes.getBills() != null) {
                            logger.info("<--- respEntity.getData(): " + queryBillExRes.getBills().toString());

                            List<ESBRequest.ListCus> listCuses = new ArrayList<>();

                            for (BillInfoBERes billInfoBE : queryBillExRes.getBills()) {
                                List<ESBRequest.BillInfo> billInfoList = new ArrayList<>();
                                ESBRequest.ListCus listCus = new ESBRequest.ListCus();
                                ESBRequest.BillInfo billInfo = new ESBRequest.BillInfo();
                                billInfo.setPaymentMethod(billInfoBE.getMonth());
                                billInfo.setBillId(billInfoBE.getBillId());
                                billInfo.setBillAmount(String.valueOf(billInfoBE.getMoneyAmount()));
                                billInfo.setSettledAmount(String.valueOf(billInfoBE.getPaymentFee()));
                                billInfo.setBillType(billInfoBE.getServiceId());
                                billInfo.setBillDesc(billInfoBE.getServiceName());
                                billInfo.setBillStatus(String.valueOf(billInfoBE.isPrepaid()));
                                billInfo.setOtherInfo(billInfoBE.getProviderName());

                                ESBRequest.CusInfo cusInfo = new ESBRequest.CusInfo(billInfoBE.getCustomerName(), billInfoBE.getCustomerId(),
                                    billInfoBE.getAddress(), String.valueOf(billInfoBE.getPaymentRule()), "", billInfoBE.getExpiredDate(), String.valueOf(billInfoBE.getMinRange()), String.valueOf(billInfoBE.getMaxRange()), billInfoBE.getProviderId(), billInfoBE.getInfoEx());

                                billInfoList.add(billInfo);
                                listCus.setCustomerInfo(cusInfo);
                                listCus.setListbillInfo(billInfoList);

                                listCuses.add(listCus);
                            }

                            logger.info("<--- listCuses: " + listCuses.toString());
                            logger.info("<--- payooResponse.getData().getBody()1: " + payooResponse.getData().getBody().toString());

                            payooResponse.getData().getBody().setListCustomer(listCuses);
//                            payooResponse.getData().getHeader().setReferenceNo(respEntity.getData().getTransactionId());
                            logger.info("<--- payooResponse.getData().getBody()2: " + payooResponse.getData().getBody().toString());

                        }
                        rescode.setErrorCode(ResponseCodeQueryBillEx.SUCCESSFUL.getResponseCode());
                        rescode.setErrorDesc(ResponseCodeQueryBillEx.SUCCESSFUL.getResponseMessage());
                        rescode.setRefCode(responseCode);
                        rescode.setRefDesc(ResponseCodeQueryBillEx.SUCCESSFUL.getResponseMessagePayoo());
                    } else if (responseCode.equals(ResponseCodeQueryBillEx.ERROR_CODE.getResponseCodePayoo())) {
                        String subReturnCode = String.valueOf(queryBillExRes.getSubReturnCode());
                        String extraInfo = queryBillExRes.getExtraInfo();

                        for (ResponseCodeQueryBillEx p : ResponseCodeQueryBillEx.values()) {
                            if (subReturnCode.equals(p.getResponseCodePayoo())) {
                                msg = BundleUtils.mapMessageQueryBillEx(subReturnCode, extraInfo, msg);
                                rescode.setErrorCode(p.getResponseCode());
                                rescode.setErrorDesc(p.getResponseMessage());
                                rescode.setRefCode(subReturnCode);
                                rescode.setRefDesc(msg == null ? p.getResponseMessagePayoo() : msg);
                                checkError = true;
                            }
                        }
                        if (!checkError) {
                            rescode.setErrorCode(ResponseCodeQueryBillEx.ERROR_CODE.getResponseCode());
                            rescode.setErrorDesc(ResponseCodeQueryBillEx.ERROR_CODE.getResponseMessage());
                            rescode.setRefCode(subReturnCode);
                            rescode.setRefDesc(msg);
                        }
                    } else if (responseCode.equals(ResponseCodeQueryBillEx.MORE_INFORMATION.getResponseCodePayoo())) {
                        if (queryBillExRes.getCustomers() != null) {
                            logger.info("<--- respEntity.getData(): " + queryBillExRes.getCustomers().toString());

                            List<ESBRequest.ListCus> listCuses = new ArrayList<>();


                            for (CustomerInfoRes customerInfo : queryBillExRes.getCustomers()) {
                                ESBRequest.ListCus listCus = new ESBRequest.ListCus();

                                ESBRequest.CusInfo cusInfo = new ESBRequest.CusInfo("",customerInfo.getCustomerId(),
                                    customerInfo.getIdentityCode(), "", "", "", "", "", "", customerInfo.getTitle());

                                listCus.setCustomerInfo(cusInfo);

                                listCuses.add(listCus);
                            }

                            logger.info("<--- listCuses: " + listCuses.toString());
                            logger.info("<--- payooResponse.getData().getBody()1: " + payooResponse.getData().getBody().toString());

                            payooResponse.getData().getBody().setListCustomer(listCuses);
//                            payooResponse.getData().getHeader().setReferenceNo(respEntity.getData().getTransactionId());
                            logger.info("<--- payooResponse.getData().getBody()2: " + payooResponse.getData().getBody().toString());

                        }
                        rescode.setErrorCode(ResponseCodeQueryBillEx.MORE_INFORMATION.getResponseCode());
                        rescode.setErrorDesc(ResponseCodeQueryBillEx.MORE_INFORMATION.getResponseMessage());
                        rescode.setRefCode(responseCode);
                        rescode.setRefDesc(ResponseCodeQueryBillEx.MORE_INFORMATION.getResponseMessagePayoo());
                    } else {
                        for (ResponseCodeQueryBillEx p : ResponseCodeQueryBillEx.values()) {
                            if (responseCode.equals(p.getResponseCodePayoo())) {
                                rescode.setErrorCode(p.getResponseCode());
                                rescode.setErrorDesc(p.getResponseMessage());
                                rescode.setRefCode(responseCode);
                                rescode.setRefDesc(p.getResponseMessagePayoo());

                                checkError = true;
                            }
                        }
                        if (!checkError) {
                            rescode.setErrorCode(ResponseCodeQueryBillEx.TIMEOUT.getResponseCode());
                            rescode.setErrorDesc(ResponseCodeQueryBillEx.TIMEOUT.getResponseMessage());
                            rescode.setRefCode(responseCode);
                            rescode.setRefDesc(msg);
                        }

                    }
                } else {
                    rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
                    rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());
                }
            } else {
                rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
                rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());
            }
        } catch (SocketTimeoutException ex) {
            logger.error("### Connection timed out: ", ex);
            rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());
        } catch (JsonProcessingException ex) {
            logger.error("### Json parse errored: ", ex);
            rescode.setErrorCode(ResponseCodeESB.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.FAIL_ESB.getResponseMessage());
        } catch (ConnectException | InterruptedIOException ex) {
            logger.error("### Connection errored: ", ex);
            rescode.setErrorCode(ResponseCodeESB.FAIL_CONNECT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.FAIL_CONNECT.getResponseMessage());
        } catch (Throwable ex) {
            logger.error("!!! Exception", ex);
            rescode.setErrorCode(ResponseCodeESB.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.FAIL_ESB.getResponseMessage());
        }
        payooResponse.setResCode(rescode);
        return payooResponse;
    }

    public ESBSettleBillResponse payOnlineBillEx(ESBSettleBillRequest req) {

        ESBSettleBillResponse payooResponse = Map.mapResponse(req);
        DataRes respEntity = null;

        String responseCode = null;
        String msg = null;
        String responseContent = null;
        String operation = "PayBillBE";
        ESBSettleBillResponse.RESCODE rescode = new ESBSettleBillResponse.RESCODE();
        boolean checkError = false;
        ObjectMapper mapper = new ObjectMapper();

        try {
            logger.info("<--- resquest payooRequest: " + req.toString());

            ESBSettleBillRequest.SettleBill listCus = req.getBody().getSettleBill();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String requestTime = sdf.format(new Date());

            logger.info("<--- resquest requestTime: " + requestTime);
            logger.info("<--- resquest listCus.getListbillInfo().size(): " + listCus.getListbillInfo().size());

            if(listCus.getListbillInfo().size() > 1) {
                rescode.setErrorCode(ResponseCodeESB.INVALID_REQUEST.getResponseCode());
                rescode.setErrorDesc(ResponseCodeESB.INVALID_REQUEST.getResponseMessage());
                payooResponse.setResCode(rescode);
                return payooResponse;
            }

            PayBillBEReq.Data body = new PayBillBEReq.Data(req.getHeader().getUserId(), listCus.getListbillInfo().get(0).getBillCode(), listCus.getCustomerInfo().getCustMobile(), listCus.getTransactionId() , listCus.getListbillInfo().get(0).getBillAmount(), listCus.getPartner().getTxnDatetime(), listCus.getPartner().getChanel(), listCus.getListbillInfo().get(0).getSettledAmount());
            String signature = RSAUtil.sign(body.toString(), serviceConfig.getCertPrefix() + serviceConfig.getCertPfx(), serviceConfig.getCertPass());
            logger.info("<--- signature: " + signature);

            PayBillBEReq payBillBEReq = new PayBillBEReq(body.toString());
            logger.info("<--- resquest PayBillBEReq: " + payBillBEReq.toString());

            PayooPartnerService service = PayooPartnerService.getInstance(null,
                req.getHeader().getDestination(),
                serviceConfig.getConnectTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getPartnerCode(),
                operation, signature, requestTime
            );
//            DEFAULT
            rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());

            Call<DataRes> call = service.PayBillBE(payBillBEReq);
            Response<DataRes> response = call.execute();
            logger.info("<--- response: " + response);
            if (response.body() != null) {
                respEntity = response.body();
                PayBillBERes payBillBERes  = mapper.readValue(respEntity.getData(), PayBillBERes.class);

                logger.info("<--- payBillBERes: " + payBillBERes.toString());

                responseCode = String.valueOf(payBillBERes.getReturnCode());
                msg = payBillBERes.getDescriptionCode();
                responseContent = respEntity.toString();
                logger.info("<--- respBody: " + responseContent);
                if (responseCode != null) {
                    payooResponse.getData().getBody().getSettleBill().getPartner().setTxnCode(payBillBERes.getOrderNo());
                    payooResponse.getData().getBody().getSettleBill().getListbillInfo().get(0).setOtherInfo((payBillBERes.getAddInfo()));

                    if (responseCode.equals(ResponseCodePayBillBE.ERROR_CODE.getResponseCodePayoo())) {
                        String subReturnCode = String.valueOf(payBillBERes.getSubReturnCode());
                        String extraInfo = payBillBERes.getExtraInfo();

                        for (ResponseCodePayBillBE p : ResponseCodePayBillBE.values()) {
                            if (subReturnCode.equals(p.getResponseCodePayoo())) {
                                msg = BundleUtils.mapMessagePayBillBE(subReturnCode, extraInfo, msg);
                                rescode.setErrorCode(p.getResponseCode());
                                rescode.setErrorDesc(p.getResponseMessage());
                                rescode.setRefCode(subReturnCode);
                                rescode.setRefDesc(msg == null ? p.getResponseMessagePayoo() : msg);
                                checkError = true;
                            }
                        }
                        if (!checkError) {
                            rescode.setErrorCode(ResponseCodePayBillBE.ERROR_CODE.getResponseCode());
                            rescode.setErrorDesc(ResponseCodePayBillBE.ERROR_CODE.getResponseMessage());
                            rescode.setRefCode(subReturnCode);
                            rescode.setRefDesc(msg);
                        }
                    } else {
                        for (ResponseCodePayBillBE p : ResponseCodePayBillBE.values()) {
                            if (responseCode.equals(p.getResponseCodePayoo())) {
                                rescode.setErrorCode(p.getResponseCode());
                                rescode.setErrorDesc(p.getResponseMessage());
                                rescode.setRefCode(responseCode);
                                rescode.setRefDesc(msg == null ? p.getResponseMessagePayoo() : msg);

                                checkError = true;
                            }
                        }
                        if (!checkError) {
                            rescode.setErrorCode(ResponseCodePayBillBE.TIMEOUT.getResponseCode());
                            rescode.setErrorDesc(ResponseCodePayBillBE.TIMEOUT.getResponseMessage());
                            rescode.setRefCode(responseCode);
                            rescode.setRefDesc(msg);
                        }

                    }
                } else {
                    rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
                    rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());
                }
            } else {
                rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
                rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());
            }
        } catch (SocketTimeoutException ex) {
            logger.error("### Connection timed out: ", ex);
            rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());
        } catch (JsonProcessingException ex) {
            logger.error("### Json parse errored: ", ex);
            rescode.setErrorCode(ResponseCodeESB.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.FAIL_ESB.getResponseMessage());
        } catch (ConnectException | InterruptedIOException ex) {
            logger.error("### Connection errored: ", ex);
            rescode.setErrorCode(ResponseCodeESB.FAIL_CONNECT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.FAIL_CONNECT.getResponseMessage());
        } catch (Throwable ex) {
            logger.error("!!! Exception", ex);
            rescode.setErrorCode(ResponseCodeESB.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.FAIL_ESB.getResponseMessage());
        }
        payooResponse.setResCode(rescode);
        return payooResponse;
    }
    public ESBQueryTransResponse getTransactionStatusBE(ESBQueryTransRequest req) {
        ESBQueryTransResponse payooResponse = Map.mapResponse(req);
            DataRes respEntity = null;

            String responseCode = null;
            String responseContent = null;
            String operation = "GetTransactionStatusBE";
            ESBQueryTransResponse.RESCODE rescode = new ESBQueryTransResponse.RESCODE();
            boolean checkError = false;
            ObjectMapper mapper = new ObjectMapper();

        try {
            logger.info("<--- resquest payooRequest: " + req.toString());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String requestTime = sdf.format(new Date());
            ESBQueryTransRequest.TransInfo transInfo = req.getBody().getTransactionInfo();
            logger.info("<--- resquest transInfo.getListTranInfo().size(): " + transInfo.getListTranInfo().size());

            if(transInfo.getListTranInfo().size() > 1) {
                rescode.setErrorCode(ResponseCodeESB.INVALID_REQUEST.getResponseCode());
                rescode.setErrorDesc(ResponseCodeESB.INVALID_REQUEST.getResponseMessage());
                payooResponse.setResCode(rescode);
                return payooResponse;
            }


            GetTransactionStatusBEReq.Data body = new GetTransactionStatusBEReq.Data(transInfo.getListTranInfo().get(0).getMsgRefid() , requestTime, transInfo.getListTranInfo().get(0).getSendDate());
            String signature = RSAUtil.sign(body.toString(),serviceConfig.getCertPrefix() + serviceConfig.getCertPfx(), serviceConfig.getCertPass());
            logger.info("<--- signature: " + signature);

            GetTransactionStatusBEReq getTransactionStatusBEReq = new GetTransactionStatusBEReq(body.toString());
            logger.info("<--- resquest GetTransactionStatusBEReq: " + getTransactionStatusBEReq.toString());

            PayooPartnerService service = PayooPartnerService.getInstance(null,
                req.getHeader().getDestination(),
                serviceConfig.getConnectTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getPartnerCode(), operation,
                signature, requestTime
            );
//            DEFAULT
            rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());


            Call<DataRes> call = service.GetTransactionStatusBE(getTransactionStatusBEReq);
            Response<DataRes> response = call.execute();
            logger.info("<--- response: " + response);
            if (response.body() != null) {
                respEntity = response.body();
                GetTransactionStatusBERes getTransactionStatusBERes = mapper.readValue(respEntity.getData(), GetTransactionStatusBERes.class);

                logger.info("<--- getTransactionStatusBERes: " + getTransactionStatusBERes.toString());

                responseCode = String.valueOf(getTransactionStatusBERes.getReturnCode());
                responseContent = respEntity.toString();
                logger.info("<--- respBody: " + responseContent);
                logger.info("<--- responseCode: " + responseCode);

                if (responseCode != null) {
                    payooResponse.getData().getBody().getTransactionInfo().getListTranInfo().get(0).setTrnRefId((getTransactionStatusBERes.getOrderNo()));

                    if (responseCode.equals(SUSCESS)) {
                        String status = getTransactionStatusBERes.getStatus();

                        for (ResponseCodeGetTransactionStatusBE p : ResponseCodeGetTransactionStatusBE.values()) {
                            if (status.equals(p.getResponseCodePayoo())) {
                                rescode.setErrorCode(p.getResponseCode());
                                rescode.setErrorDesc(p.getResponseMessage());
                                rescode.setRefCode(status);
                                rescode.setRefDesc(p.getResponseMessagePayoo());
                                checkError = true;
                            }
                        }
                        if (!checkError) {
                            rescode.setErrorCode(ResponseCodeESB.QUERY_TRANSACTION_FAIL.getResponseCode());
                            rescode.setErrorDesc(ResponseCodeESB.QUERY_TRANSACTION_FAIL.getResponseMessage());
                            rescode.setRefCode(status);
                        }
                    } else {
                        for (ResponseCodeGetTransactionStatusBE p : ResponseCodeGetTransactionStatusBE.values()) {
                            if (responseCode.equals(p.getResponseCodePayoo())) {
                                rescode.setErrorCode(p.getResponseCode());
                                rescode.setErrorDesc(p.getResponseMessage());
                                rescode.setRefCode(responseCode);
                                rescode.setRefDesc(p.getResponseMessagePayoo());

                                checkError = true;
                            }
                        }
                        if (!checkError) {
                            rescode.setErrorCode(ResponseCodeESB.QUERY_TRANSACTION_FAIL.getResponseCode());
                            rescode.setErrorDesc(ResponseCodeESB.QUERY_TRANSACTION_FAIL.getResponseMessage());
                            rescode.setRefCode(responseCode);
                        }
                    }
                } else {
                    rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
                    rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());
                }
            } else {
                rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
                rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());
            }
        } catch (SocketTimeoutException ex) {
            logger.error("### Connection timed out: ", ex);
            rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());
        } catch (JsonProcessingException ex) {
            logger.error("### Json parse errored: ", ex);
            rescode.setErrorCode(ResponseCodeESB.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.FAIL_ESB.getResponseMessage());
        } catch (ConnectException | InterruptedIOException ex) {
            logger.error("### Connection errored: ", ex);
            rescode.setErrorCode(ResponseCodeESB.FAIL_CONNECT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.FAIL_CONNECT.getResponseMessage());
        } catch (Throwable ex) {
            logger.error("!!! Exception", ex);
            rescode.setErrorCode(ResponseCodeESB.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.FAIL_ESB.getResponseMessage());
        }
        payooResponse.setResCode(rescode);
        return payooResponse;
    }
}
