package com.lpb.esb.service.sms.process;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.sms.model.entities.MsgInputTempEntity;
import org.springframework.stereotype.Component;

/**
 * Created by tudv1 on 2021-08-13
 */
@Component
public interface SmsSendProcess {
    public ExecuteModel sendMessage(String message, String chatId);
    public ExecuteModel sendMessage(MsgInputTempEntity msgInputTempEntity);
}
