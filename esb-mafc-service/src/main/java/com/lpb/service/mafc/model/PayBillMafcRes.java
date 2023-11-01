package com.lpb.service.mafc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayBillMafcRes extends AbstractBody {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("errros")
    private String errros;

    @JsonProperty("result")
    private Data result;

    public static class Data extends AbstractBody {
        @JsonProperty("v_result")
        private String vResult;

        @JsonProperty("v_message")
        private String vMessage;

        public String getvResult() {
            return vResult;
        }

        public void setvResult(String vResult) {
            this.vResult = vResult;
        }

        public String getvMessage() {
            return vMessage;
        }

        public void setvMessage(String vMessage) {
            this.vMessage = vMessage;
        }
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrros() {
        return errros;
    }

    public void setErrros(String errros) {
        this.errros = errros;
    }

    public Data getResult() {
        return result;
    }

    public void setResult(Data result) {
        this.result = result;
    }
}
