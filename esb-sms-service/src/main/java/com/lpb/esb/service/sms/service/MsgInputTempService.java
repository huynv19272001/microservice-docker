package com.lpb.esb.service.sms.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.sms.model.entities.MsgInputTempEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tudv1 on 2021-08-24
 */
@Service
public interface MsgInputTempService {
    ResponseModel saveBatchMsg(List<MsgInputTempEntity> list);

    void scanAndSendMessage();

    ResponseModel saveMsgChannel(MsgInputTempEntity entity);
}
