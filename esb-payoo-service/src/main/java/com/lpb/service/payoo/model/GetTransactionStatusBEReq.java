package com.lpb.service.payoo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GetTransactionStatusBEReq extends AbstractBody {
    @JsonProperty(value = "Data", required = true)
    private String data;

    @JsonCreator
    public GetTransactionStatusBEReq(String data) {
        this.data = data;
    }

    public static class Data extends AbstractBody {

        @JsonProperty(value = "SystemTrace", required = true)
        private String systemTrace;

        @JsonProperty(value = "RequestTime", required = true)
        private String requestTime;

        @JsonProperty(value = "TransactionTime", required = true)
        private String transactionTime;

        @JsonCreator
        public Data(String systemTrace, String requestTime, String transactionTime) {
            this.systemTrace = systemTrace;
            this.requestTime = requestTime;
            this.transactionTime = transactionTime;
        }


        public String getSystemTrace() {
            return systemTrace;
        }

        public String getRequestTime() {
            return requestTime;
        }


        public void setSystemTrace(String systemTrace) {
            this.systemTrace = systemTrace;
        }

        public void setRequestTime(String requestTime) {
            this.requestTime = requestTime;
        }

        public String getTransactionTime() {
            return transactionTime;
        }

        public void setTransactionTime(String transactionTime) {
            this.transactionTime = transactionTime;
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
