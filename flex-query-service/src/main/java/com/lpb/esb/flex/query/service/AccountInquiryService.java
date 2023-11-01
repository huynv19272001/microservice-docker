package com.lpb.esb.flex.query.service;

import java.util.List;

import com.lpb.esb.flex.query.model.data.LpbAccountDetail;
import com.lpb.esb.flex.query.model.soapclient.GetAccountDetailListWSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lpb.esb.flex.query.model.data.LpbAccount;
import com.lpb.esb.flex.query.model.data.LpbAccountInfo;
import com.lpb.esb.flex.query.model.soapclient.GetAccountListWSClient;
import com.lpb.esb.service.common.model.response.ResponseModel;

@Service
public class AccountInquiryService {

	@Autowired
	private GetAccountListWSClient getAccountListWSClient;
    @Autowired
    private GetAccountDetailListWSClient getAccountDetailListWSClient;

	public ResponseModel<List<LpbAccount>> inquiryAccountInfo(LpbAccountInfo accountInfo) {
		return getAccountListWSClient.findAccountInfo(accountInfo);
	}

    public ResponseModel<List<LpbAccountDetail>> inquiryAccountDetail(LpbAccountInfo accountInfo) {
        return getAccountDetailListWSClient.findAccountDetail(accountInfo);
    }

}
