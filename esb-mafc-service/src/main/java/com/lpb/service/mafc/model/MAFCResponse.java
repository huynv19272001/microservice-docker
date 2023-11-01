package com.lpb.service.mafc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MAFCResponse extends AbstractBody{
    public MAFCResponse(){

    }
    @JsonProperty(value = "resCode", required = false)
    private RESCODE resCode;

    @JsonProperty(value = "data", required = false)
    private DATA data;


    public static class RESCODE extends AbstractBody {
        @JsonProperty(value = "errorCode", required = false)
        private String errorCode;

        @JsonProperty(value = "errorDesc", required = false)
        private String errorDesc;

        @JsonProperty(value = "refCode", required = false)
        private String refCode;

        @JsonProperty(value = "refDesc", required = false)
        private String refDesc;

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorDesc() {
            return errorDesc;
        }

        public void setErrorDesc(String errorDesc) {
            this.errorDesc = errorDesc;
        }

        public String getRefCode() {
            return refCode;
        }

        public void setRefCode(String refCode) {
            this.refCode = refCode;
        }

        public String getRefDesc() {
            return refDesc;
        }

        public void setRefDesc(String refDesc) {
            this.refDesc = refDesc;
        }
    }

    public static class DATA extends AbstractBody {
        @JsonProperty(value = "body", required = false)
        private Body body;

        @JsonProperty(value = "header", required = false)
        private MAFCRequest.Header header;

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }

        public MAFCRequest.Header getHeader() {
            return header;
        }

        public void setHeader(MAFCRequest.Header header) {
            this.header = header;
        }
    }

    public static class Body extends AbstractBody {
        @JsonProperty(value = "service", required = false)
        private MAFCRequest.Service service;

        @JsonProperty(value = "listCustomer", required = false)
        private List<MAFCRequest.ListCus> listCustomer;

        public MAFCRequest.Service getService() {
            return service;
        }

        public void setService(MAFCRequest.Service service) {
            this.service = service;
        }

        public List<MAFCRequest.ListCus> getListCustomer() {
            return listCustomer;
        }

        public void setListCustomer(List<MAFCRequest.ListCus> listCustomer) {
            this.listCustomer = listCustomer;
        }
    }
    public RESCODE getResCode() {
        return resCode;
    }

    public void setResCode(RESCODE resCode) {
        this.resCode = resCode;
    }

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }
}

