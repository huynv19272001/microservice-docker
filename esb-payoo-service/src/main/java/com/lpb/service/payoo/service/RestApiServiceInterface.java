package com.lpb.service.payoo.service;

import com.lpb.service.payoo.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestApiServiceInterface {

    @POST("execute")
    Call<DataRes> QueryBillBE(@Body QueryBillExReq request);

    @POST("execute")
    Call<DataRes> PayBillBE(@Body PayBillBEReq request);

    @POST("execute")
    Call<DataRes> GetTransactionStatusBE(@Body GetTransactionStatusBEReq request);
}

