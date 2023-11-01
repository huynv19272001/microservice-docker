package com.lpb.service.vimo.service;


import com.lpb.service.vimo.model.*;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestApiServiceInterface {

    @POST("querybill")
    Call<QueryBillRes> querybill(@Body RequestBody request);

    @POST("paybillv2")
    Call<PayBillv2Res> paybillv2(@Body RequestBody request);

    @POST("paybillpartial")
    Call<PayBillPartialRes> paybillpartial(@Body RequestBody request);

    @POST("getaddfee")
    Call<GetAddFeeRes> getaddfee(@Body RequestBody request);
}
