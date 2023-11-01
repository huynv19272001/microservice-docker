package com.lpb.esb.service.tct.util;

import com.google.gson.JsonParser;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.tct.model.config.TctFileConfig;
import com.lpb.esb.service.tct.model.request.RequestData;
import com.lpb.esb.service.tct.model.request.TctHeader;
import com.lpb.esb.service.tct.model.request.dto.EsbTctRequestDTO;
import com.lpb.esb.service.tct.model.request.dto.TctHeaderDTO;
import com.lpb.esb.service.tct.model.request.dto.TctRequestDTO;
import com.lpb.esb.service.tct.util.cert.XMLSigner;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by tudv1 on 2022-02-25
 */
@Component
@Log4j2
public class BuildMessageUtils {
    @Autowired
    TctFileConfig tctFileConfig;
    @Autowired
    LogicUtils logicUtils;

    public String buildTctHeader(TctHeader tctHeader) {
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlHeader();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase, tctHeader.getVersion(),
            tctHeader.getSenderCode(), tctHeader.getSenderName(),
            tctHeader.getReceiverCode(), tctHeader.getReceiverName(),
            tctHeader.getTranCode(), tctHeader.getMsgId(), tctHeader.getMsgRefId(),
            tctHeader.getIdLink(), tctHeader.getSendDate(), tctHeader.getOriginalCode(),
            tctHeader.getOriginalName(), tctHeader.getOriginalDate());
    }

    public String buildTctDTOHeader(TctHeaderDTO tctHeader) {
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlHeader();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase, tctHeader.getVersion(),
            tctHeader.getSenderCode(), tctHeader.getSenderName(),
            tctHeader.getReceiverCode(), tctHeader.getReceiverName(),
            tctHeader.getTranCode(), tctHeader.getMsgId(), tctHeader.getMsgRefid(),
            tctHeader.getIdLink(), tctHeader.getSendDate(), tctHeader.getOriginalCode(),
            tctHeader.getOriginalName(), tctHeader.getOriginalDate());
    }

    public String buildBodyLPTB(RequestData data) {
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlBodyLptb();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , data.getBody().getSoToKhai()
            , data.getBody().getMaSoThue()
//            , data.getBody().getLoaiThue()
        );
    }

    public String buildBodyMst(TctRequestDTO data) {
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlBodyMst();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , data.getTctBody().getMaSoThue()
        );
    }

    public String buildBodyThuNopMst(TctRequestDTO data) {
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlBodyThuNopMst();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , data.getTctBody().getMaSoThue()
        );
    }

    public String buildBodyCMND(TctRequestDTO data) {
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlBodyCmnd();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , data.getTctBody().getSoCMND());
    }


    public String buildBodyTCN(RequestData data) {
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlBodyTcn();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , data.getBody().getMaSoThue()
            , data.getBody().getMaCqThu()
            , data.getBody().getLoaiThue()
        );
    }

    public String buildBodyTND(RequestData data) {
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlBodyTnd();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , data.getBody().getMaHs()
            , data.getBody().getSo());
    }

    public String buildBodyNopChungTu(RequestData data) {
        new JSONObject(data.getBody().getRow());
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlBodyNopChungTu();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , logicUtils.convertJsonToXml(
                logicUtils.jsonKeysUpper(
                    new JsonParser().parse(
                        (new JSONObject(data.getBody().getRow())).toString()
                    ).getAsJsonObject()
                ).toString()
            )
        );
    }

    public String buildBodyHuyChungTu(RequestData data) {
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlBodyHuyChungTu();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , data.getBody().getMaNhtm()
            , data.getBody().getSoChungtu());
    }

    public String buildBodyTruyVanChungTu(TctRequestDTO data) {
        final String path = tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlBodyTruyVanChungTu();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path)).replaceAll("\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format(xmlBase
            , data.getTctBody().getMaSoThue()
            , data.getTctBody().getMaSoThueNNT()
            , data.getTctBody().getSoChungtu()
            , data.getTctBody().getNgayChungtuTungay()
            , data.getTctBody().getNgayChungtuDenngay()
            , data.getTctBody().getTrangThai());
    }

    public String buildBodyDanhmucTruyvan(ServiceInfo serviceInfo) {
        return String.format(serviceInfo.getUdf1()
            , serviceInfo.getUdf2()
            , serviceInfo.getUdf3()
        );
    }

    public String buildMessageRequest(String header, String body) {
        String data = String.format("<DATA>%s%s</DATA>", header, body);
        log.info("data request: {}", data);
        String dataRequest = XMLSigner.sign(data
            , tctFileConfig.getFilePrefix() + tctFileConfig.getTctXmlBodyRequest()
            , tctFileConfig.getCertPrefix() + tctFileConfig.getCertPfx()
        );
        log.info("message: {}", dataRequest);
        return dataRequest;
    }

}
