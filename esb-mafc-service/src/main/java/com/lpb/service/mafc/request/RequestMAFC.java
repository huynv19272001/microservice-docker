package com.lpb.service.mafc.request;

import com.lpb.service.mafc.service.RequestMAFCService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lpb.service.mafc.config.ServiceConfig;
import com.lpb.service.mafc.model.*;
import com.lpb.service.mafc.service.MAFCPartnerService;

import com.lpb.service.mafc.utils.Map;
import com.lpb.service.mafc.utils.ResponseCode;
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
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class RequestMAFC implements RequestMAFCService {
    private static final Logger logger = LogManager.getLogger(RequestMAFC.class);
    @Autowired
    ServiceConfig serviceConfig;

    @Override
    public MAFCResponse getLoanInfor(MAFCRequest req) {
        MAFCResponse mafcResponse = Map.mapResponse(req);

        GetLoanInforRes respEntity = null;
        boolean responseCode = false;
        String msg = null;
        String responseContent = null;
        MAFCResponse.RESCODE rescode = new MAFCResponse.RESCODE();
        try {
            logger.info("<--- getLoanInfor+++++++++++++++++++++++++++: " + req.getBody().getService().getRequestAccount());

            MAFCPartnerService service = MAFCPartnerService.getInstance(null,
                req.getHeader().getDestination(),
                serviceConfig.getConnectTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getAuthorization()
            );
            logger.info("<--- resquest: " + req.toString());
//            DEFAULT
            rescode.setErrorCode(ResponseCode.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCode.FAIL_ESB.getResponseMessage());

            Call<GetLoanInforRes> call = service.getLoanInfor(req.getBody().getService().getRequestAccount());
            Response<GetLoanInforRes> response = call.execute();
            logger.info("<--- response: " + response);
            if (response.body() != null) {
                respEntity = response.body();
                responseCode = respEntity.getSuccess();
                msg = respEntity.getErrros();
                responseContent = respEntity.toString();
                logger.info("<--- respBody: " + responseContent);
                if (responseCode) {
                    if (respEntity.getResult() != null && respEntity.getResult().size() > 0) {
                        List<MAFCRequest.ListCus> listCuses = new ArrayList<>();
                        List<MAFCRequest.BillInfo> billInfoList = new ArrayList<>();

                        for (GetLoanInforRes.Data data : respEntity.getResult()) {
                            MAFCRequest.BillInfo billInfo = new MAFCRequest.BillInfo();
                            MAFCRequest.ListCus listCus = new MAFCRequest.ListCus();
                            MAFCRequest.CusInfo cusInfo = new MAFCRequest.CusInfo(data.getvCustname(), "",
                                "", "", "", "", "", "", "", "");

                            billInfo.setBillCode(data.getvAgreeid());
                            billInfo.setBillId(data.getvDuedate());
                            billInfo.setBillAmount(String.valueOf(data.getvNetReceivable()));
                            billInfo.setSettledAmount(String.valueOf(data.getvInstlamt()));
                            billInfoList.add(billInfo);
                            listCus.setCustomerInfo(cusInfo);
                            listCus.setListbillInfo(billInfoList);
                            listCuses.add(listCus);
                        }

                        mafcResponse.getData().getBody().setListCustomer(listCuses);
                        rescode.setErrorCode(ResponseCode.SUCCESS.getResponseCode());
                        rescode.setErrorDesc(ResponseCode.SUCCESS.getResponseMessage());
                        rescode.setRefCode(String.valueOf(responseCode));
                        rescode.setRefDesc(msg);
                    } else {
                        rescode.setErrorCode(ResponseCode.NO_DATA.getResponseCode());
                        rescode.setErrorDesc("ID/ agreement ID not exist");
                        rescode.setRefCode(String.valueOf(responseCode));
                        rescode.setRefDesc(msg);
                    }
                } else {
                    rescode.setErrorCode(ResponseCode.FAIL.getResponseCode());
                    rescode.setErrorDesc(msg);
                    rescode.setRefCode(String.valueOf(responseCode));
                    rescode.setRefDesc(msg);
                }
            }
        } catch (SocketTimeoutException ex) {
            logger.error("### Connection timed out: ", ex);
            rescode.setErrorCode(ResponseCode.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCode.TIMEOUT.getResponseMessage());
        } catch (JsonProcessingException ex) {
            logger.error("### Json parse errored: ", ex);
            rescode.setErrorCode(ResponseCode.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCode.FAIL_ESB.getResponseMessage());
        } catch (ConnectException | InterruptedIOException ex) {
            logger.error("### Connection errored: ", ex);
            rescode.setErrorCode(ResponseCode.FAIL_CONNECT.getResponseCode());
            rescode.setErrorDesc(ResponseCode.FAIL_CONNECT.getResponseMessage());
        } catch (Throwable ex) {
            logger.error("!!! Exception", ex);
            rescode.setErrorCode(ResponseCode.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCode.FAIL_ESB.getResponseMessage());
        }
        mafcResponse.setResCode(rescode);
        return mafcResponse;

    }

    @Override
    public MAFCSettleBillResponse payBillMafc(MAFCSettleBillRequest req) {
        MAFCSettleBillResponse mafcResponse = Map.mapResponse(req);

        PayBillMafcRes respEntity = null;
        boolean responseCode = false;
        String msg = null;
        String responseContent = null;
        MAFCSettleBillResponse.RESCODE rescode = new MAFCSettleBillResponse.RESCODE();

        try {
            logger.info("<--- resquest MAFCRequest: " + req.toString());
            MAFCSettleBillRequest.SettleBill listCus = req.getBody().getSettleBill();
            logger.info("<--- resquest listCus.getListbillInfo().size(): " + listCus.getListbillInfo().size());

            if(listCus.getListbillInfo().size() > 1) {
                rescode.setErrorCode(ResponseCode.INVALID_REQUEST.getResponseCode());
                rescode.setErrorDesc(ResponseCode.INVALID_REQUEST.getResponseMessage());
                mafcResponse.setResCode(rescode);
                return mafcResponse;
            }

            PayBillMafcReq payBillMafcReq = new PayBillMafcReq(listCus.getTransactionId(), listCus.getListbillInfo().get(0).getBillCode(), Integer.valueOf(listCus.getListbillInfo().get(0).getBillAmount()), serviceConfig.getChannel(), serviceConfig.getCollector());

            logger.info("<--- resquest PayBillMafcReq: " + payBillMafcReq.toString());


            MAFCPartnerService service = MAFCPartnerService.getInstance(null,
                req.getHeader().getDestination(),
                serviceConfig.getConnectTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getSocketTimeout(),
                serviceConfig.getAuthorization()
            );
//            DEFAULT
            rescode.setErrorCode(ResponseCode.FAIL.getResponseCode());
            rescode.setErrorCode(ResponseCode.FAIL.getResponseMessage());

            Call<PayBillMafcRes> call = service.payBillMafc(payBillMafcReq);
            Response<PayBillMafcRes> response = call.execute();
            logger.info("<--- response: " + response);
            if (response.body() != null) {
                respEntity = response.body();
                responseCode = respEntity.getSuccess();
                msg = respEntity.getErrros();
                responseContent = respEntity.toString();
                logger.info("<--- respBody: " + responseContent);
                if (responseCode) {
                    if (respEntity.getResult() != null) {
                        if (respEntity.getResult().getvResult().equals("1")) {
                            rescode.setErrorCode(ResponseCode.SUCCESS.getResponseCode());
                            rescode.setErrorDesc(ResponseCode.SUCCESS.getResponseMessage());
                            rescode.setRefCode(String.valueOf(responseCode));
                            rescode.setRefDesc(respEntity.getResult().getvMessage());
                        } else {
                            rescode.setErrorCode(ResponseCode.FAIL.getResponseCode());
                            rescode.setErrorDesc(ResponseCode.FAIL.getResponseMessage());
                            rescode.setRefCode(String.valueOf(responseCode));
                            rescode.setRefDesc(respEntity.getResult().getvMessage());
                        }
                    } else {
                        rescode.setErrorCode(ResponseCode.NO_DATA.getResponseCode());
                        rescode.setErrorDesc(ResponseCode.NO_DATA.getResponseMessage());
                        rescode.setRefCode(String.valueOf(responseCode));
                        rescode.setRefDesc(msg);
                    }
                } else {
                    rescode.setErrorCode(ResponseCode.FAIL.getResponseCode());
                    rescode.setErrorDesc(ResponseCode.FAIL.getResponseMessage());
                    rescode.setRefCode(String.valueOf(responseCode));
                    rescode.setRefDesc(msg);
                }
            }
        } catch (SocketTimeoutException ex) {
            logger.error("### Connection timed out: ", ex);
            rescode.setErrorCode(ResponseCode.TIMEOUT.getResponseCode());
            rescode.setErrorDesc(ResponseCode.TIMEOUT.getResponseMessage());
        } catch (JsonProcessingException ex) {
            logger.error("### Json parse errored: ", ex);
            rescode.setErrorCode(ResponseCode.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCode.FAIL_ESB.getResponseMessage());
        } catch (ConnectException | InterruptedIOException ex) {
            logger.error("### Connection errored: ", ex);
            rescode.setErrorCode(ResponseCode.FAIL_CONNECT.getResponseCode());
            rescode.setErrorDesc(ResponseCode.FAIL_CONNECT.getResponseMessage());
        } catch (Throwable ex) {
            logger.error("!!! Exception", ex);
            rescode.setErrorCode(ResponseCode.FAIL_ESB.getResponseCode());
            rescode.setErrorDesc(ResponseCode.FAIL_ESB.getResponseMessage());
        }
        mafcResponse.setResCode(rescode);
        return mafcResponse;
    }


}
