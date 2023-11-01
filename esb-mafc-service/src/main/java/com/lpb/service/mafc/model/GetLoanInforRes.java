package com.lpb.service.mafc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class GetLoanInforRes extends AbstractBody {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("errros")
    private String errros;

    @JsonProperty("result")
    private List<Data> result;

    public static class Data extends AbstractBody {
        @JsonProperty("v_agreeid")
        private String vAgreeid;

        @JsonProperty("v_custname")
        private String vCustname;

        @JsonProperty("v_net_receivable")
        private int vNetReceivable;

        @JsonProperty("v_instlamt")
        private int vInstlamt;

        @JsonProperty("v_duedate")
        private String vDuedate;

        public String getvAgreeid() {
            return vAgreeid;
        }

        public void setvAgreeid(String vAgreeid) {
            this.vAgreeid = vAgreeid;
        }

        public String getvCustname() {
            return vCustname;
        }

        public void setvCustname(String vCustname) {
            this.vCustname = vCustname;
        }

        public int getvNetReceivable() {
            return vNetReceivable;
        }

        public void setvNetReceivable(int vNetReceivable) {
            this.vNetReceivable = vNetReceivable;
        }

        public int getvInstlamt() {
            return vInstlamt;
        }

        public void setvInstlamt(int vInstlamt) {
            this.vInstlamt = vInstlamt;
        }

        public String getvDuedate() {
            return vDuedate;
        }

        public void setvDuedate(String vDuedate) {
            this.vDuedate = vDuedate;
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

    public List<Data> getResult() {
        return result;
    }

    public void setResult(List<Data> result) {
        this.result = result;
    }
}
