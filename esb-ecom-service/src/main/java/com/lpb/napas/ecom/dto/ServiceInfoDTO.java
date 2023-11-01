package com.lpb.napas.ecom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ServiceInfoDTO {
    private String serviceId;
    private String productCode;
    private String urlApi;
    private String connectorURL;
    private String connectPort;
    private String methodAction;
    private String UDF1;
    private String UDF2;
    private String UDF3;
    private String UDF4;
    private String hasRole;

    public ServiceInfoDTO(String serviceId, String productCode, String urlApi, String connectorURL, String connectPort, String methodAction, String udf1, String udf2, String udf3, String udf4) {
        this.serviceId = serviceId;
        this.productCode = productCode;
        this.urlApi = urlApi;
        this.connectorURL = connectorURL;
        this.connectPort = connectPort;
        this.methodAction = methodAction;
        this.UDF1 = udf1;
        this.UDF2 = udf2;
        this.UDF3 = udf3;
        this.UDF4 = udf4;
    }

}
