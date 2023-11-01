package com.lpb.service.payoo.service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import com.lpb.service.payoo.model.*;
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

    public class PayooPartnerService {
        private static final Logger logger = LogManager.getLogger(PayooPartnerService.class.getSimpleName());

        private static final Charset UTF8 = Charset.forName("UTF-8");

        private final RestApiServiceInterface restService;

        private static PayooPartnerService restApiService = null;

        public static PayooPartnerService getInstance(Proxy proxy, String baseUrl, int connectionTimeout, int readTimeout, int writeTimeout, String partnerCode, String operation, String signature, String requestTime) {
                synchronized (PayooPartnerService.class) {
                    restApiService = new PayooPartnerService(proxy, baseUrl, connectionTimeout, readTimeout, writeTimeout, partnerCode, operation, signature, requestTime);
                }
            return restApiService;
        }

        private PayooPartnerService(Proxy proxy, String baseUrl, int connectionTimeout, int readTimeout, int writeTimeout, String partnerCode, String operation, String signature, String requestTime) {
            final OkHttpClient.Builder builder = getUnsafeOkHttpClient();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);

            builder.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.addHeader("Content-Type", "application/Json");
                requestBuilder.addHeader("PARTNERCODE",partnerCode);
                requestBuilder.addHeader("Operation", operation);
                requestBuilder.addHeader("RequestTime", requestTime);
                requestBuilder.addHeader("Signature", signature);

                logger.info("---> RequestTime : " + requestTime);

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
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

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

        public Call<DataRes> QueryBillBE(QueryBillExReq request) {
            return restService.QueryBillBE(request);
        }

        public Call<DataRes> PayBillBE(PayBillBEReq request) {
            return restService.PayBillBE(request);
        }

        public Call<DataRes> GetTransactionStatusBE(GetTransactionStatusBEReq request) {
            return restService.GetTransactionStatusBE(request);
        }
    }
