package com.lpb.service.vimo.service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import com.lpb.service.vimo.model.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import okhttp3.*;
import okio.Buffer;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.schedulers.Schedulers;

import retrofit2.Call;
import retrofit2.http.*;

public class VIMOPartnerService {
    private static final Logger logger = LogManager.getLogger(VIMOPartnerService.class.getSimpleName());

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final RestApiServiceInterface restService;

    private static VIMOPartnerService restApiService = null;

    public static VIMOPartnerService getInstance(Proxy proxy, String baseUrl, int connectionTimeout, int readTimeout, int writeTimeout, String userName, String password) {
        if (restApiService == null) {
            synchronized (VIMOPartnerService.class) {
                restApiService = new VIMOPartnerService(proxy, baseUrl, connectionTimeout, readTimeout, writeTimeout, userName, password);
            }
        }
        return restApiService;
    }

    private VIMOPartnerService(Proxy proxy, String baseUrl, int connectionTimeout, int readTimeout, int writeTimeout, String userName, String password) {
        final OkHttpClient.Builder builder = getUnsafeOkHttpClient();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        String plainCredentials = userName + ":" + password;
        String base64Credentials = new String(Base64.getEncoder().encode(plainCredentials.getBytes()));
        // Create authorization header
        String authorizationHeader = "Basic " + base64Credentials;

        builder.addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.addHeader("Authorization", authorizationHeader);
            requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//            requestBuilder.addHeader("Accept", "application/json");
            Request request = requestBuilder.build();
            if (!request.url().toString().contains("token")) {
                logger.info("---> request : " + request.toString());
            }
            Response resp = chain.proceed(request);
            try {
                logger.info("<--- response: " + resp.toString());
                ResponseBody responseBody = resp.body();
                if (responseBody != null) {
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); // Buffer the entire body.
                    Buffer buffer = source.buffer();
                    logger.info("<--- response body: " + buffer.clone().readString(UTF8));
                } else {
                    logger.info("<--- response body: " + responseBody);
                }
            } catch (IOException e) {
                logger.error("ERROR - logging response", e);
            }
            return resp;
        });

        OkHttpClient okHttpClient = builder
            .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .proxy(proxy)
            .build();

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(rxAdapter)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build();

        restService = retrofit.create(RestApiServiceInterface.class);
    }

    private OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    //                        @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    //                        @SuppressLint("TrustAllX509TrustManager")
                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            X509TrustManager trustManager = (X509TrustManager) trustAllCerts[0];

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier((hostname, session) -> true);

            return builder;
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * destroy all instance if it has been initialized
     */
    public static void destroy() {
        restApiService = null;
    }

    public Call<QueryBillRes> querybill(RequestBody request) {
        return restService.querybill(request);
    }

    public Call<PayBillv2Res> paybillv2(RequestBody request) {
        return restService.paybillv2(request);
    }

    public Call<PayBillPartialRes> paybillpartial(RequestBody request) {
        return restService.paybillpartial(request);
    }

    public Call<GetAddFeeRes> getaddfee(RequestBody request) {
        return restService.getaddfee(request);
    }
}
