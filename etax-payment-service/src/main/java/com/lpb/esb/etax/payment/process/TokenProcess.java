/**
 * @author Trung.Nguyen
 * @date 08-Jul-2022
 * */
package com.lpb.esb.etax.payment.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lpb.esb.etax.payment.model.response.ConfrmTransResponse;
import com.lpb.esb.etax.payment.repository.EsbEtaxTokenRepository;
import com.lpb.esb.etax.payment.tokenization.TokenVault;
import com.lpb.esb.etax.payment.util.FlexCubsUtils;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class TokenProcess {
	
	@Autowired
	private FlexCubsUtils flexMsgUtils;
	@Autowired
	private EsbEtaxTokenRepository esbEtaxTokenRepository;
	@Autowired
	private TokenVault tokenVault;
	
	public ConfrmTransResponse createToken() {
		return null;
	}
	
	public ConfrmTransResponse closeToken() {
		return null;
	}

}
