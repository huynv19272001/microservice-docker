package com.lpb.service.mafc.service;

import com.lpb.service.mafc.model.GetLoanInforRes;
import com.lpb.service.mafc.model.PayBillMafcReq;
import com.lpb.service.mafc.model.PayBillMafcRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RestApiServiceInterface {
    @GET("collectionmafc/{agreeIdOrCif}")
    Call<GetLoanInforRes> getLoanInfor(@Path("agreeIdOrCif") String agreeIdOrCif);

    @POST("PayBillMafc")
    Call<PayBillMafcRes> payBillMafc(@Body PayBillMafcReq request);

}
