package com.lpb.esb.service.sms.service.impl;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.common.utils.code.ExecuteCode;
import com.lpb.esb.service.sms.model.entities.MsgInputTempEntity;
import com.lpb.esb.service.sms.process.SmsSendProcess;
import com.lpb.esb.service.sms.repositories.MsgInputTempRepository;
import com.lpb.esb.service.sms.service.MsgInputTempService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * Created by tudv1 on 2021-08-24
 */
@Service
@Log4j2
public class MsgInputTempServiceImpl implements MsgInputTempService {
    @Autowired
    MsgInputTempRepository msgInputTempRepository;

    @Autowired
    @Qualifier("smsSend3rdProcessImpl")
    SmsSendProcess smsSend3rdProcessImpl;

    @Override
    public ResponseModel saveBatchMsg(List<MsgInputTempEntity> list) {
        list.forEach(x -> {
            x.setStatus("N");
        });
        msgInputTempRepository.saveAll(list);

        LpbResCode resCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Save batch message success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(resCode)
            .build();
        return responseModel;
    }

    //    @Scheduled(cron = "3 * * * * ?")
    @Scheduled(fixedDelay = 60 * 1000, initialDelay = 5 * 1000)
    @Override
    public void scanAndSendMessage() {
        List<MsgInputTempEntity> listNew = msgInputTempRepository.findAllByStatus("N");
        for (MsgInputTempEntity msgInputTempEntity : listNew) {
            if (msgInputTempEntity.getSendTime().getTime() > System.currentTimeMillis()) {
                // send message here
                ExecuteModel executeModel = smsSend3rdProcessImpl.sendMessage(msgInputTempEntity);
                // end send message
                // Check result for update db
                if (executeModel.getExecuteCode() == ExecuteCode.SUCCESS) {
                    msgInputTempEntity.setStatus("D");
                }
            }
        }

        // update result
        msgInputTempRepository.saveAll(listNew);
    }

    @Override
    public ResponseModel saveMsgChannel(MsgInputTempEntity entity) {
        entity.setStatus("N");
        entity.setCreateDt(new Date(System.currentTimeMillis()));
        msgInputTempRepository.save(entity);

        LpbResCode lpbResCode = LpbResCode.builder()
            .errorCode(EsbErrorCode.SUCCESS.label)
            .errorDesc("Save message success")
            .build();
        ResponseModel responseModel = ResponseModel.builder()
            .resCode(lpbResCode)
            .build();
        return responseModel;
    }
}
