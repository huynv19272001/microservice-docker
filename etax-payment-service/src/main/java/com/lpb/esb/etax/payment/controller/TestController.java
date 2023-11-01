package com.lpb.esb.etax.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.etax.payment.model.data.SmsInfo;
import com.lpb.esb.etax.payment.model.entity.EsbEtaxToken;
import com.lpb.esb.etax.payment.model.entity.EtaxPaymentInfo;
import com.lpb.esb.etax.payment.model.entity.EtaxReceiptInfo;
import com.lpb.esb.etax.payment.model.response.DefaultResponse;
import com.lpb.esb.etax.payment.process.SmsProcess;
import com.lpb.esb.etax.payment.repository.EsbEtaxTokenRepository;
import com.lpb.esb.etax.payment.repository.EtaxPaymentInfoRepository;
import com.lpb.esb.etax.payment.repository.EtaxReceiptInfoRepository;
import com.lpb.esb.etax.payment.util.DocxTemplateUtils;
import com.lpb.esb.etax.payment.util.FlexCubsUtils;
import com.lpb.esb.etax.payment.util.LogicUtils;
import com.lpb.esb.etax.payment.util.RestClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by tudv1 on 2022-06-22
 */
@RestController
public class TestController {
    @Autowired
    EtaxPaymentInfoRepository etaxPaymentInfoRepository;
    @Autowired
    EtaxReceiptInfoRepository etaxReceiptInfoRepository;
    @Autowired
    EsbEtaxTokenRepository esbEtaxTokenRepository;
    @Autowired
    DocxTemplateUtils docxTemplateUtils;
    @Autowired
    FlexCubsUtils flexCubsUtils;
    @Autowired
    RestClientUtils restClientUtils;
    @Autowired
    LogicUtils logicUtils;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    SmsProcess smsProcess;

    @GetMapping(value = "test/receipt")
    public ResponseEntity testReceipt(@RequestParam(value = "ref-number") String referenceNumber) throws Exception {
        EtaxReceiptInfo etaxReceiptInfo = etaxReceiptInfoRepository.findById(referenceNumber).orElse(null);
        EtaxPaymentInfo etaxPaymentInfo = etaxPaymentInfoRepository.findById(referenceNumber).orElse(null);
        if (etaxPaymentInfo != null && etaxReceiptInfo != null) {
            String inputName = "etax-payment-service/file/docx/" + referenceNumber + ".docx";
            String outputDir = "etax-payment-service/file/pdf/";
            docxTemplateUtils.fillTemplateReceipt(
                etaxReceiptInfo
                , etaxPaymentInfo
                , "etax-payment-service/file/templates/receipt.docx"
                , inputName
            );
            String fileInputName = referenceNumber + ".docx";
            Resource resource = logicUtils.load("etax-payment-service/file/docx/", fileInputName);
            Resource pdf =  restClientUtils.sendFileConvertBody(resource, outputDir);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; attachment; filename=" + URLDecoder.decode(pdf.getFilename(), String.valueOf(StandardCharsets.ISO_8859_1)))
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
        }
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/check-cot/{svrid}", produces = "application/json")
	public String checkCOT(@PathVariable("svrid") String serviceId) {
		return flexCubsUtils.checkCutOffTime(serviceId);
	}

    @GetMapping(value = "/get-token/{id}", produces = "application/json")
   	public EsbEtaxToken getToken(@PathVariable("id") String requestId) {
   		return esbEtaxTokenRepository.findNewRequest2Token(requestId);
   	}

    @GetMapping(value = "/get-otoken/{id}", produces = "application/json")
   	public EsbEtaxToken getTokenO(@PathVariable("id") String requestId) {
   		return esbEtaxTokenRepository.findTokenByUnLinkReq(requestId);
   	}

    @PostMapping(value = "test/snake-case", produces = "application/json")
    public ResponseEntity snakeCase(@RequestBody SmsInfo smsInfo) {
        try {
//            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCaseStrategy.SNAKE_CASE);;
            DefaultResponse defaultResponse =  smsProcess.sendSms(smsInfo);
            return ResponseEntity.ok(defaultResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }

    }
}
