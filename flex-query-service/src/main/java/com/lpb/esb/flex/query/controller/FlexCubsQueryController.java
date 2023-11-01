package com.lpb.esb.flex.query.controller;

import java.util.List;

import com.lpb.esb.flex.query.model.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lpb.esb.flex.query.service.AccountInquiryService;
import com.lpb.esb.flex.query.service.CustomerInquiryService;
import com.lpb.esb.service.common.model.response.ResponseModel;

@RestController
@RequestMapping(value = "/rest/api")
public class FlexCubsQueryController {

	@Autowired
	private AccountInquiryService accountInquiryService;
	@Autowired
	private CustomerInquiryService customerInquiryService;

	@PostMapping(value = "/query-account-info", produces = "application/json")
	public ResponseModel<List<LpbAccount>> inquiryAccountInfo(@RequestBody LpbAccountInfo accountInfo) {
		return accountInquiryService.inquiryAccountInfo(accountInfo);
	}

    @PostMapping(value = "/query-account-detail", produces = "application/json")
    public ResponseModel<List<LpbAccountDetail>> inquiryAccountDetail(@RequestBody LpbAccountInfo accountInfo) {
        return accountInquiryService.inquiryAccountDetail(accountInfo);
    }

	@PostMapping(value = "/query-customer-info", produces = "application/json")
	public ResponseModel<List<LpbCustomer>> inquiryCustomerInfo(@RequestBody LpbCustomerInfo customerInfo) {
		return customerInquiryService.inquiryCustomerInfo(customerInfo);
	}

}
