package com.lpb.esb.flex.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lpb.esb.flex.query.model.data.LpbCustomer;
import com.lpb.esb.flex.query.model.data.LpbCustomerInfo;
import com.lpb.esb.flex.query.model.soapclient.GetCustomerListWSClient;
import com.lpb.esb.service.common.model.response.ResponseModel;

@Service
public class CustomerInquiryService {
	
	@Autowired
	private GetCustomerListWSClient getCustomerListWSClient;
	
	public ResponseModel<List<LpbCustomer>> inquiryCustomerInfo(LpbCustomerInfo customerInfo) {
		return getCustomerListWSClient.findCustomerInfo(customerInfo);
	}
	
}
