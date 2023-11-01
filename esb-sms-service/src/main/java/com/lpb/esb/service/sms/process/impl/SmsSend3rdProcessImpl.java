package com.lpb.esb.service.sms.process.impl;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.utils.code.ExecuteCode;
import com.lpb.esb.service.sms.model.entities.MsgInputTempEntity;
import com.lpb.esb.service.sms.process.SmsSendProcess;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * Created by tudv1 on 2021-08-24
 */
@Component("smsSend3rdProcessImpl")
@Log4j2
public class SmsSend3rdProcessImpl implements SmsSendProcess {
    @Override
    public ExecuteModel sendMessage(String message, String chatId) {
        ExecuteModel executeModel = ExecuteModel.builder()
            .executeCode(ExecuteCode.FAIL)
            .build();
        return executeModel;
    }

    @Override
    public ExecuteModel sendMessage(MsgInputTempEntity msgInputTempEntity) {
        log.info("Send message to 3rd: {}", msgInputTempEntity.toString());
        return null;
    }
}
