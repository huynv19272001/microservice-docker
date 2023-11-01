package com.lpb.service.sql.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDTO {
    private String SERVICE_ID;
    private String PRODUCT_CODE;
    private String REQUEST_ACCOUNT;
    private String RECEIVE_ACCOUNT;
    private String MERCHANT_ID;
//	@Override
//	public String toString() {
//		return "<SERVICE><SERVICE_ID>" + SERVICE_ID + "</SERVICE_ID><PRODUCT><PRODUCT_CODE>"
//				+ PRODUCT_CODE + "</PRODUCT_CODE><MERCHANT_ID>"+MERCHANT_ID+"</MERCHANT_ID></PRODUCT>"
//				+ "<SERVICE_INFO><REQUEST_ACCOUNT>" + REQUEST_ACCOUNT+"</REQUEST_ACCOUNT>"
//				+ "<RECEIVE_ACCOUNT>" + RECEIVE_ACCOUNT + "</RECEIVE_ACCOUNT>%s</SERVICE_INFO></SERVICE>";
//	}
}
