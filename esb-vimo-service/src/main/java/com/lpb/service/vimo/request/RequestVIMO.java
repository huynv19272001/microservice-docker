package com.lpb.service.vimo.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.lpb.service.vimo.service.RequestVIMOService;
import com.lpb.service.vimo.config.ServiceConfig;
import com.lpb.service.vimo.model.*;
import com.lpb.service.vimo.service.VIMOPartnerService;

import com.lpb.service.vimo.util.Aes256;
import com.lpb.service.vimo.util.Map;
import com.lpb.service.vimo.util.ResponseCode;
import com.lpb.service.vimo.util.ResponseCodeESB;
import lombok.extern.log4j.Log4j2;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

@Component
@Log4j2
public class RequestVIMO implements RequestVIMOService {
    private static final Logger logger = LogManager.getLogger(RequestVIMO.class);
    private Gson gson = new Gson();
    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public ESBResponse querybill(ESBRequest req) {
        ESBResponse vimoResponse = Map.mapResponse(req);

        QueryBillRes respEntity = null;
        String responseCode = null;
        String msg = null;
        String responseContent = null;
        ESBResponse.RESCODE rescode = new ESBResponse.RESCODE();

        boolean checkError = false;
        try {
            QueryBillReq queryBillReq = new QueryBillReq(req.getHeader().getMsgId(), req.getBody().getService().getServiceId(), req.getBody().getService().getMerchantId(), req.getBody().getService().getRequestAccount());

            String dataEncrypt = "";
            // Encrypt data
            try {
                dataEncrypt = Aes256.encrypt(queryBillReq.toString(), serviceConfig.getMcEncryptKey());
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestBody formBody = new FormBody.Builder()
                .add("fnc", "querybill")
                .add("merchantcode", serviceConfig.getMerchantCode())
                .add("data", dataEncrypt)
                .add("checksum", DigestUtils.md5Hex(serviceConfig.getMcCode() + dataEncrypt + serviceConfig.getMcChecksumKey()))
                .build();

            VIMOPartnerService service = VIMOPartnerService.getInstance(null,
                req.getHeader().getDestination(),
                serviceConfig.getConnectTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getMcAuthUser(),
                serviceConfig.getMcAuthPass()
            );
//            DEFAULT
            rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());

            Call<QueryBillRes> call = service.querybill(formBody);
            Response<QueryBillRes> response = call.execute();
            logger.info("<--- response: " + response);
            if (response.body() != null) {
                respEntity = response.body();
                responseCode = respEntity.getErrorCode();
                msg = respEntity.getErrorMessage();
                responseContent = respEntity.toString();
                logger.info("<--- respBody: " + responseContent);
                if (responseCode != null) {
                    if (responseCode.equals(ResponseCode.SUCCESSFUL.getResponseCodeVimo())) {
                        if (respEntity.getData() != null) {

                            List<ESBRequest.ListCus> listCuses = new ArrayList<>();
                            List<ESBRequest.BillInfo> billInfoList = new ArrayList<>();

                            ESBRequest.ListCus listCus = new ESBRequest.ListCus();
                            CustomerOtherInfo customerOtherInfo = null;
                            if(!respEntity.getData().getCustomerInfo().getCustomerOtherInfo().isEmpty()) {
                                customerOtherInfo = gson.fromJson(respEntity.getData().getCustomerInfo().getCustomerOtherInfo(), CustomerOtherInfo.class);
                            }
                            ESBRequest.CusInfo cusInfo = new ESBRequest.CusInfo(respEntity.getData().getCustomerInfo().getCustomerName(), respEntity.getData().getCustomerInfo().getCustomerCode(),
                                respEntity.getData().getCustomerInfo().getCustomerAddress(), "", "", "", (customerOtherInfo == null) ? "" : customerOtherInfo.getCustomerPhone() , "", "", (customerOtherInfo == null) ? "" : customerOtherInfo.getTaxCode() );

                            listCus.setCustomerInfo(cusInfo);

                            for (QueryBillRes.Detail detail : respEntity.getData().getBillDetail()) {
                                ESBRequest.BillInfo billInfo = new ESBRequest.BillInfo();
                                billInfo.setBillCode(detail.getBillNumber());
                                billInfo.setBillId(detail.getPeriod());
                                billInfo.setBillAmount(String.valueOf(detail.getAmount()));
                                billInfo.setBillType(detail.getBillType());
                                OthInfoQueryBillRes othInfo = gson.fromJson(detail.getOtherInfo(), OthInfoQueryBillRes.class);
                                billInfo.setOtherInfo(othInfo.toString());
                                billInfoList.add(billInfo);
                            }
                            listCus.setListbillInfo(billInfoList);

                            listCuses.add(listCus);

                            vimoResponse.getData().getBody().setListCustomer(listCuses);
                        }
                        rescode.setErrorCode(ResponseCode.SUCCESSFUL.getResponseCode());
                        rescode.setErrorDesc(ResponseCode.SUCCESSFUL.getResponseMessage());
                        rescode.setRefCode(responseCode);
                        rescode.setRefDesc(msg);
                    } else {
                        for (ResponseCode p : ResponseCode.values()) {
                            if (responseCode.equals(p.getResponseCodeVimo())) {
                                rescode.setErrorCode(p.getResponseCode());
                                rescode.setErrorDesc(p.getResponseMessage());
                                rescode.setRefCode(responseCode);
                                rescode.setRefDesc(msg);

                                checkError = true;
                            }
                        }
                        if (!checkError) {
                            rescode.setErrorCode(ResponseCode.TIMEOUT.getResponseCode());
                            rescode.setErrorDesc(ResponseCode.TIMEOUT.getResponseMessage());
                            rescode.setRefCode(responseCode);
                            rescode.setRefDesc(msg);
                        }

                    }
                } else {
                    rescode.setErrorCode(ResponseCode.TIMEOUT.getResponseCode());
                    rescode.setErrorDesc(ResponseCode.TIMEOUT.getResponseMessage());
                }
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
        vimoResponse.setResCode(rescode);
        return vimoResponse;
    }

    @Override
    public ESBSettleBillResponse paybillv2(ESBSettleBillRequest req) {
        ESBSettleBillResponse vimoResponse = Map.mapResponse(req);

        PayBillv2Res respEntity = null;
        String responseCode = null;
        String msg = null;
        String responseContent = null;
        ESBSettleBillResponse.RESCODE rescode = new ESBSettleBillResponse.RESCODE();
        boolean checkError = false;
        try {
            ESBSettleBillRequest.SettleBill listCus = req.getBody().getSettleBill();

            if(listCus.getListbillInfo().size() > 1) {
                rescode.setErrorCode(ResponseCodeESB.INVALID_REQUEST.getResponseCode());
                rescode.setErrorDesc(ResponseCodeESB.INVALID_REQUEST.getResponseMessage());
                vimoResponse.setResCode(rescode);
                return vimoResponse;
            }
            OthInfoPayBillv2Req othInfo = null;
            List<PayBillv2Req.Payment> paymentList = new ArrayList<>();
            if(!listCus.getListbillInfo().get(0).getOtherInfo().isEmpty()){
                String[] lstOtherInfor = listCus.getListbillInfo().get(0).getOtherInfo().split("\\|");
                if(lstOtherInfor.length != 9){
                    rescode.setErrorCode(ResponseCodeESB.INVALID_REQUEST.getResponseCode());
                    rescode.setErrorDesc(ResponseCodeESB.INVALID_REQUEST.getResponseMessage());
                    vimoResponse.setResCode(rescode);
                    return vimoResponse;
                }
                String addFee = (getValue(String.valueOf(lstOtherInfor[3])).isEmpty() || getValue(String.valueOf(lstOtherInfor[3])).equals("0")) ? "" : getValue(String.valueOf(lstOtherInfor[3]));
                othInfo = new OthInfoPayBillv2Req(getValue(lstOtherInfor[0]), getValue(lstOtherInfor[1]), getValue(lstOtherInfor[2]), addFee , getValue(lstOtherInfor[4])
                    , getValue(lstOtherInfor[5]), getValue(lstOtherInfor[6]), getValue(lstOtherInfor[7]), getValue(lstOtherInfor[8]));

            }
            PayBillv2Req.Payment payment = new PayBillv2Req.Payment(listCus.getListbillInfo().get(0).getBillCode(), listCus.getListbillInfo().get(0).getBillId(), Integer.valueOf(listCus.getListbillInfo().get(0).getBillAmount()), listCus.getListbillInfo().get(0).getBillType(), (othInfo == null) ? "" : othInfo.toString());
            paymentList.add(payment);

            PayBillv2Req payBillv2Req = new PayBillv2Req(req.getHeader().getMsgId(), req.getBody().getSettleBill().getService().getServiceId(), req.getBody().getSettleBill().getService().getProductCode(), req.getBody().getSettleBill().getService().getRequestAccount(), paymentList);

            logger.info("<--- resquest PayBillv2Req: " + payBillv2Req.toString());

            String dataEncrypt = "";
            // Encrypt data
            try {
                dataEncrypt = Aes256.encrypt(payBillv2Req.toString(), serviceConfig.getMcEncryptKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestBody formBody = new FormBody.Builder()
                .add("fnc", "paybillv2")
                .add("merchantcode", serviceConfig.getMerchantCode())
                .add("data", dataEncrypt)
                .add("checksum", DigestUtils.md5Hex(serviceConfig.getMcCode() + dataEncrypt + serviceConfig.getMcChecksumKey()))
                .build();

            VIMOPartnerService service = VIMOPartnerService.getInstance(null,
                req.getHeader().getDestination(),
                serviceConfig.getConnectTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getMcAuthUser(),
                serviceConfig.getMcAuthPass()
            );
            //            DEFAULT
            rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());

            Call<PayBillv2Res> call = service.paybillv2(formBody);
            Response<PayBillv2Res> response = call.execute();
            logger.info("<--- response: " + response);
            if (response.body() != null) {
                respEntity = response.body();
                responseCode = respEntity.getErrorCode();
                msg = respEntity.getErrorMessage();
                responseContent = respEntity.toString();
                logger.info("<--- respBody: " + responseContent);
                if (responseCode != null) {
                    if (responseCode.equals(ResponseCode.SUCCESSFUL.getResponseCodeVimo())) {
                        if (respEntity.getData() != null) {
                            ESBSettleBillRequest.CusInfo cusInfo = new ESBSettleBillRequest.CusInfo(respEntity.getData().getCustomerInfo().getCustomerName(), respEntity.getData().getCustomerInfo().getCustomerCode(),
                                respEntity.getData().getCustomerInfo().getCustomerAddress(), "", "", "", "", "", "", respEntity.getData().getCustomerInfo().getCustomerOtherInfo());

                            vimoResponse.getData().getBody().getSettleBill().setTransactionId(respEntity.getData().getTransactionId());
                            vimoResponse.getData().getBody().getSettleBill().setCustomerInfo(cusInfo);
                            vimoResponse.getData().getHeader().setReferenceNo(respEntity.getData().getTransactionId());
                        }
                        rescode.setErrorCode(ResponseCode.SUCCESSFUL.getResponseCode());
                        rescode.setErrorDesc(ResponseCode.SUCCESSFUL.getResponseMessage());
                        rescode.setRefCode(responseCode);
                        rescode.setRefDesc(msg);
                    } else {
                        for (ResponseCode p : ResponseCode.values()) {
                            if (responseCode.equals(p.getResponseCodeVimo())) {
                                rescode.setErrorCode(p.getResponseCode());
                                rescode.setErrorDesc(p.getResponseMessage());
                                rescode.setRefCode(responseCode);
                                rescode.setRefDesc(msg);

                                checkError = true;
                            }
                        }
                        if (!checkError) {
                            rescode.setErrorCode(ResponseCode.TIMEOUT.getResponseCode());
                            rescode.setErrorDesc(ResponseCode.TIMEOUT.getResponseMessage());
                            rescode.setRefCode(responseCode);
                            rescode.setRefDesc(msg);
                        }
                    }
                } else {
                    rescode.setErrorCode(ResponseCode.TIMEOUT.getResponseCode());
                    rescode.setErrorDesc(ResponseCode.TIMEOUT.getResponseMessage());
                }
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
        vimoResponse.setResCode(rescode);
        return vimoResponse;
    }

    @Override
    public ESBSettleBillResponse paybillpartial(ESBSettleBillRequest req) {
        ESBSettleBillResponse vimoResponse = Map.mapResponse(req);

        PayBillPartialRes respEntity = null;
        String responseCode = null;
        String msg = null;
        String responseContent = null;
        ESBSettleBillResponse.RESCODE rescode = new ESBSettleBillResponse.RESCODE();
        boolean checkError = false;
        try {
            ESBSettleBillRequest.SettleBill listCus = req.getBody().getSettleBill();

            if(listCus.getListbillInfo().size() > 1) {
                rescode.setErrorCode(ResponseCodeESB.INVALID_REQUEST.getResponseCode());
                rescode.setErrorDesc(ResponseCodeESB.INVALID_REQUEST.getResponseMessage());
                vimoResponse.setResCode(rescode);
                return vimoResponse;
            }

            OthInfoPayBillPartialReq othInfo = null;
            List<PayBillPartialReq.Payment> paymentList = new ArrayList<>();

            if(!listCus.getListbillInfo().get(0).getOtherInfo().isEmpty()){
                String[] lstOtherInfor = listCus.getListbillInfo().get(0).getOtherInfo().split("\\|");
                if(lstOtherInfor.length != 10){
                    rescode.setErrorCode(ResponseCodeESB.INVALID_REQUEST.getResponseCode());
                    rescode.setErrorDesc(ResponseCodeESB.INVALID_REQUEST.getResponseMessage());
                    vimoResponse.setResCode(rescode);
                    return vimoResponse;
                }
                String addFee = (getValue(String.valueOf(lstOtherInfor[3])).isEmpty() || getValue(String.valueOf(lstOtherInfor[3])).equals("0")) ? "" : getValue(String.valueOf(lstOtherInfor[3]));

                othInfo = new OthInfoPayBillPartialReq(getValue(String.valueOf(lstOtherInfor[0])), getValue(String.valueOf(lstOtherInfor[1])), getValue(String.valueOf(lstOtherInfor[2])), addFee, getValue(String.valueOf(lstOtherInfor[4]))
                    , getValue(String.valueOf(lstOtherInfor[5])), getValue(String.valueOf(lstOtherInfor[6])), getValue(String.valueOf(lstOtherInfor[7])), getValue(String.valueOf(lstOtherInfor[8])), getValue(String.valueOf(lstOtherInfor[9])));

            }
            logger.info("<*************************************resquest othInfo**************************************: " + othInfo.toString());
            PayBillPartialReq.Payment payment = new PayBillPartialReq.Payment(listCus.getListbillInfo().get(0).getBillCode(), listCus.getListbillInfo().get(0).getBillId(), Integer.valueOf(listCus.getListbillInfo().get(0).getBillAmount()), listCus.getListbillInfo().get(0).getBillType(), (othInfo == null) ? "" : othInfo.toString());
            paymentList.add(payment);

            PayBillPartialReq payBillPartialReq = new PayBillPartialReq(req.getHeader().getMsgId(), req.getBody().getSettleBill().getService().getServiceId(), req.getBody().getSettleBill().getService().getProductCode(), req.getBody().getSettleBill().getService().getRequestAccount(), paymentList);

            logger.info("<--- resquest payBillPartialReq: " + payBillPartialReq.toString());

            String dataEncrypt = "";
            // Encrypt data
            try {
                dataEncrypt = Aes256.encrypt(payBillPartialReq.toString(), serviceConfig.getMcEncryptKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestBody formBody = new FormBody.Builder()
                .add("fnc", "paybillpartial")
                .add("merchantcode", serviceConfig.getMerchantCode())
                .add("data", dataEncrypt)
                .add("checksum", DigestUtils.md5Hex(serviceConfig.getMcCode() + dataEncrypt + serviceConfig.getMcChecksumKey()))
                .build();

            VIMOPartnerService service = VIMOPartnerService.getInstance(null,
                req.getHeader().getDestination(),
                serviceConfig.getConnectTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getMcAuthUser(),
                serviceConfig.getMcAuthPass()
            );
            //            DEFAULT
            rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());

            Call<PayBillPartialRes> call = service.paybillpartial(formBody);
            Response<PayBillPartialRes> response = call.execute();
            logger.info("<--- response: " + response);
            if (response.body() != null) {
                respEntity = response.body();
                responseCode = respEntity.getErrorCode();
                msg = respEntity.getErrorMessage();
                responseContent = respEntity.toString();
                logger.info("<--- respBody: " + responseContent);
                if (responseCode != null) {
                    if (responseCode.equals(ResponseCode.SUCCESSFUL.getResponseCodeVimo())) {
                        if (respEntity.getData() != null) {
                            ESBSettleBillRequest.CusInfo cusInfo = new ESBSettleBillRequest.CusInfo(respEntity.getData().getCustomerInfo().getCustomerName(), respEntity.getData().getCustomerInfo().getCustomerCode(),
                                respEntity.getData().getCustomerInfo().getCustomerAddress(), "", "", "", "", "", "", respEntity.getData().getCustomerInfo().getCustomerOtherInfo());

                            vimoResponse.getData().getBody().getSettleBill().setTransactionId(respEntity.getData().getTransactionId());
                            vimoResponse.getData().getBody().getSettleBill().setCustomerInfo(cusInfo);
                        }
                        rescode.setErrorCode(ResponseCode.SUCCESSFUL.getResponseCode());
                        rescode.setErrorDesc(ResponseCode.SUCCESSFUL.getResponseMessage());
                        rescode.setRefCode(responseCode);
                        rescode.setRefDesc(msg);
                    } else {
                        for (ResponseCode p : ResponseCode.values()) {
                            if (responseCode.equals(p.getResponseCodeVimo())) {
                                rescode.setErrorCode(p.getResponseCode());
                                rescode.setErrorDesc(p.getResponseMessage());
                                rescode.setRefCode(responseCode);
                                rescode.setRefDesc(msg);

                                checkError = true;
                            }
                        }
                        if (!checkError) {
                            rescode.setErrorCode(ResponseCode.TIMEOUT.getResponseCode());
                            rescode.setErrorDesc(ResponseCode.TIMEOUT.getResponseMessage());
                            rescode.setRefCode(responseCode);
                            rescode.setRefDesc(msg);
                        }
                    }
                } else {
                    rescode.setErrorCode(ResponseCode.TIMEOUT.getResponseCode());
                    rescode.setErrorDesc(ResponseCode.TIMEOUT.getResponseMessage());
                }
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
        vimoResponse.setResCode(rescode);
        return vimoResponse;
    }

    @Override
    public ESBResponse getaddfee(ESBRequest req) {
        ESBResponse vimoResponse = Map.mapResponse(req);

        GetAddFeeRes respEntity = null;
        String responseCode = null;
        String msg = null;
        String responseContent = null;
        ESBResponse.RESCODE rescode = new ESBResponse.RESCODE();
        boolean checkError = false;
        try {
            if(req.getBody().getListCustomer().size() > 1) {
                rescode.setErrorCode(ResponseCodeESB.INVALID_REQUEST.getResponseCode());
                rescode.setErrorDesc(ResponseCodeESB.INVALID_REQUEST.getResponseMessage());
                vimoResponse.setResCode(rescode);
                return vimoResponse;
            }

            if(req.getBody().getListCustomer().get(0).getListbillInfo().size() > 1) {
                rescode.setErrorCode(ResponseCodeESB.INVALID_REQUEST.getResponseCode());
                rescode.setErrorDesc(ResponseCodeESB.INVALID_REQUEST.getResponseMessage());
                vimoResponse.setResCode(rescode);
                return vimoResponse;
            }
            ESBRequest.BillInfo billInfo = req.getBody().getListCustomer().get(0).getListbillInfo().get(0);

            GetAddFeeReq getAddFeeReq = new GetAddFeeReq(req.getHeader().getMsgId(), req.getBody().getService().getServiceId(), req.getBody().getService().getMerchantId(), req.getBody().getService().getRequestAccount(), Integer.valueOf(billInfo.getBillAmount()));
            logger.info("<--- resquest getAddFeeReq: " + getAddFeeReq.toString());

            String dataEncrypt = "";
            // Encrypt data
            try {
                dataEncrypt = Aes256.encrypt(getAddFeeReq.toString(), serviceConfig.getMcEncryptKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
            RequestBody formBody = new FormBody.Builder()
                .add("fnc", "getaddfee")
                .add("merchantcode", serviceConfig.getMerchantCode())
                .add("data", dataEncrypt)
                .add("checksum", DigestUtils.md5Hex(serviceConfig.getMcCode() + dataEncrypt + serviceConfig.getMcChecksumKey()))
                .build();

            VIMOPartnerService service = VIMOPartnerService.getInstance(null,
                req.getHeader().getDestination(),
                serviceConfig.getConnectTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getMcAuthUser(),
                serviceConfig.getMcAuthPass()
            );
            //            DEFAULT
            rescode.setErrorCode(ResponseCodeESB.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCodeESB.TIMEOUT.getResponseMessage());

            Call<GetAddFeeRes> call = service.getaddfee(formBody);
            Response<GetAddFeeRes> response = call.execute();
            logger.info("<--- response: " + response);
            if (response.body() != null) {
                respEntity = response.body();
                responseCode = respEntity.getErrorCode();
                msg = respEntity.getErrorMessage();
                responseContent = respEntity.toString();
                logger.info("<--- respBody: " + responseContent);
                if (responseCode != null) {
                    if (responseCode.equals(ResponseCode.SUCCESSFUL.getResponseCode())) {
                        if (respEntity.getData() != null) {
                            vimoResponse.getData().getBody().getListCustomer().get(0).getListbillInfo().get(0).setSettledAmount(String.valueOf(respEntity.getData().getAddfee()));
                        }
                        rescode.setErrorCode(ResponseCode.SUCCESSFUL.getResponseCode());
                        rescode.setErrorDesc(ResponseCode.SUCCESSFUL.getResponseMessage());
                        rescode.setRefCode(responseCode);
                        rescode.setRefDesc(msg);
                    } else {
                        for (ResponseCode p : ResponseCode.values()) {
                            if (responseCode.equals(p.getResponseCodeVimo())) {
                                rescode.setErrorCode(p.getResponseCode());
                                rescode.setErrorDesc(p.getResponseMessage());
                                rescode.setRefCode(responseCode);
                                rescode.setRefDesc(msg);

                                checkError = true;
                            }
                        }
                        if (!checkError) {
                            rescode.setErrorCode(ResponseCode.TIMEOUT.getResponseCode());
                            rescode.setErrorDesc(ResponseCode.TIMEOUT.getResponseMessage());
                            rescode.setRefCode(responseCode);
                            rescode.setRefDesc(msg);
                        }
                    }
                } else {
                    rescode.setErrorCode(ResponseCode.TIMEOUT.getResponseCode());
                    rescode.setErrorDesc(ResponseCode.TIMEOUT.getResponseMessage());
                }
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
        vimoResponse.setResCode(rescode);
        return vimoResponse;
    }

    private String getValue(String otherInfor){
        String value = "";
        try {
            String[] lstValue = otherInfor.split("=");
            if(lstValue.length == 2){
                value = lstValue[1];
            }
        } catch (Exception e){
            logger.info("!!! Exception:", e);
            value = "";
        }
        return value;
    }
}
