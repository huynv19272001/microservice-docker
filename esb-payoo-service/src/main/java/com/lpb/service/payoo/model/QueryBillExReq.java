package com.lpb.service.payoo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryBillExReq extends AbstractBody {
    @JsonProperty(value = "Data", required = true)
    private String data;

    public static class Data extends AbstractBody {
        @JsonProperty(value = "UserId", required = true)
        private String userId;

        @JsonProperty(value = "SearchValue", required = true)
        private String searchValue;

        @JsonProperty(value = "ProviderId", required = true)
        private String providerId;

        @JsonProperty(value = "ServiceId", required = true)
        private String serviceId;

        @JsonProperty(value = "AddingInput", required = false)
        private String addingInput;

        @JsonProperty(value = "ChannelPerform", required = true)
        private String channelPerform;

        @JsonCreator
        public Data(String userId, String searchValue, String serviceId, String providerId, String addingInput, String channelPerform) {
            this.userId = userId;
            this.searchValue = searchValue;
            this.serviceId = serviceId;
            this.providerId = providerId;
            this.addingInput = addingInput;
            this.channelPerform = channelPerform;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(String searchValue) {
            this.searchValue = searchValue;
        }

        public String getProviderId() {
            return providerId;
        }

        public void setProviderId(String providerId) {
            this.providerId = providerId;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getAddingInput() {
            return addingInput;
        }

        public void setAddingInput(String addingInput) {
            this.addingInput = addingInput;
        }

        public String getChannelPerform() {
            return channelPerform;
        }

        public void setChannelPerform(String channelPerform) {
            this.channelPerform = channelPerform;
        }
    }

    @JsonCreator
    public QueryBillExReq(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
