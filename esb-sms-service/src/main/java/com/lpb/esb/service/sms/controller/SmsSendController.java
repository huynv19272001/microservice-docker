package com.lpb.esb.service.sms.controller;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.common.utils.PageUtils;
import com.lpb.esb.service.common.utils.code.EsbErrorCode;
import com.lpb.esb.service.common.utils.code.ExecuteCode;
import com.lpb.esb.service.sms.model.dto.MsgInputTempDTO;
import com.lpb.esb.service.sms.model.entities.MsgInputTempEntity;
import com.lpb.esb.service.sms.model.request.EsbSmsRequest;
import com.lpb.esb.service.sms.model.request.MessageInputTempRequest;
import com.lpb.esb.service.sms.model.request.TelegramRequest;
import com.lpb.esb.service.sms.service.MsgInputTempService;
import com.lpb.esb.service.sms.service.SmsSendService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tudv1 on 2021-08-13
 */
@RestController
@RequestMapping(value = "sms/send", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SmsSendController {

    @Autowired
    SmsSendService smsSendService;
    @Autowired
    MsgInputTempService msgInputTempService;

    @RequestMapping(value = "telegram", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> sendTelegram(@RequestBody TelegramRequest body) {
        ExecuteModel executeModel = smsSendService.sendTelegram(body.getMessage(), body.getChatId());
        ResponseModel responseModel = ResponseModel.builder()
            .data(executeModel.getData())
            .build();
        LpbResCode resCode = new LpbResCode();
        responseModel.setResCode(resCode);
        if (executeModel.getExecuteCode() == ExecuteCode.SUCCESS) {
            resCode.setErrorCode(EsbErrorCode.SUCCESS.label);
            resCode.setErrorDesc("Send message to tele success");
            return ResponseEntity.ok(responseModel);
        } else {
            resCode.setErrorCode(EsbErrorCode.FAIL.label);
            resCode.setErrorDesc("Send message to tele fail");
            return ResponseEntity.badRequest().body(responseModel);
        }
    }

    @RequestMapping(value = "batch", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> sendBatch(@RequestBody List<MsgInputTempDTO> inputs) {
        List<MsgInputTempEntity> list = PageUtils.copyBeans(inputs, MsgInputTempEntity.class);
        ResponseModel responseModel = msgInputTempService.saveBatchMsg(list);
        if (EsbErrorCode.SUCCESS.label.equalsIgnoreCase(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok()
                .body(responseModel);
        } else {
            return ResponseEntity.badRequest()
                .body(responseModel);
        }
    }


    @RequestMapping(value = "channel", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> sendChanel(@RequestBody MessageInputTempRequest request) {
        MsgInputTempEntity entity = new MsgInputTempEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setBatchNumber("Channel".toUpperCase());
        ResponseModel responseModel = msgInputTempService.saveMsgChannel(entity);
        responseModel.setData(request);
        if (EsbErrorCode.SUCCESS.label.equalsIgnoreCase(responseModel.getResCode().getErrorCode())) {
            return ResponseEntity.ok()
                .body(responseModel);
        } else {
            return ResponseEntity.badRequest()
                .body(responseModel)
                ;
        }
    }

    @RequestMapping(value = "esb", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> sendNoti(@RequestBody EsbSmsRequest esbSmsRequest) {
        smsSendService.sendSmsEsb(esbSmsRequest);

        return null;
    }

    @RequestMapping(value = "ftel", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> sendFtel(@RequestBody EsbSmsRequest esbSmsRequest) {
        ResponseModel responseModel = smsSendService.sendSmsFtel(esbSmsRequest);
        if (!responseModel.getResCode().getErrorCode().equals("ESB-000")) {
            return ResponseEntity.badRequest().body(responseModel);
        } else {
            return ResponseEntity.ok(responseModel);
        }
    }

    @RequestMapping(value = "vnpay", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> sendVnpay(@RequestBody EsbSmsRequest esbSmsRequest) {
        ResponseModel responseModel = smsSendService.sendSmsVnpay(esbSmsRequest);
        if (!responseModel.getResCode().getErrorCode().equals("ESB-000")) {
            return ResponseEntity.badRequest().body(responseModel);
        } else {
            return ResponseEntity.ok(responseModel);
        }
    }

    @RequestMapping(value = "v1/vnpay", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseModel> sendXmlVnpay(@RequestBody EsbSmsRequest esbSmsRequest) {
        ResponseModel responseModel = smsSendService.sendSmsVnpXml(esbSmsRequest);
        if (!responseModel.getResCode().getErrorCode().equals("00")) {
            return ResponseEntity.badRequest().body(responseModel);
        } else {
            return ResponseEntity.ok(responseModel);
        }
    }
}
