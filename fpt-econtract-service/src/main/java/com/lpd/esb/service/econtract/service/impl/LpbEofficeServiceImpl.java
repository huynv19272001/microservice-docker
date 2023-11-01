package com.lpd.esb.service.econtract.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpd.esb.service.econtract.model.ErrorBodyDTO;
import com.lpd.esb.service.econtract.model.EsbDTO;
import com.lpd.esb.service.econtract.model.LpbRequestDTO;
import com.lpd.esb.service.econtract.model.ResponseDTO;
import com.lpd.esb.service.econtract.model.config.EofficeXmlConfig;
import com.lpd.esb.service.econtract.service.LpbEofficeService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class LpbEofficeServiceImpl implements LpbEofficeService {
    @Autowired
    RestTemplate restTemplateLB;
    @Autowired
    EofficeXmlConfig eofficeXmlConfig;

    @Override
    public ResponseDTO sendFile(LpbRequestDTO request) {
        EsbDTO header = EsbDTO.builder()
            .serviceId(request.getHeader().getServiceId())
            .productCode(request.getHeader().getProductCode())
            .build();

        ResponseDTO response = new ResponseDTO();

        List<ServiceInfo> serviceInfo = ConnectInfoService.getServiceInfo(restTemplateLB,
            request.getHeader().getServiceId(), request.getHeader().getProductCode(), "FEService");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        headers.setAccept(Arrays.asList(MediaType.TEXT_XML));
        headers.set("SOAPAction", "http://tempuri.org/SaveFileSig");

        //NTLM Authentication
        String userName = serviceInfo.get(0).getUdf1();
        String password = serviceInfo.get(0).getUdf2();
        String domain = serviceInfo.get(0).getUdf3();

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(
            userName, password, null, domain));
        CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultCredentialsProvider(credsProvider)
            .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplateNTLM = new RestTemplate(requestFactory);

        //create xml request
        String path = eofficeXmlConfig.getFilePrefix() + eofficeXmlConfig.getEofficeReq();
        String xmlBase = null;
        try {
            xmlBase = FileUtils.readFileToString(new File(path), (Charset) null);
        } catch (IOException e) {
            log.error("xmlBase error: " + e);
        }
        String reqXml = String.format(xmlBase, request.getBody().getId(), request.getBody().getFileName(),
            request.getBody().getBytes());
//        log.info(reqXml);

        try {
            //send xml request
            HttpEntity<String> entity = new HttpEntity<>(reqXml, headers);
            ResponseEntity<String> econtractResponse = restTemplateNTLM.postForEntity(
                serviceInfo.get(0).getConnectorURL(), entity, String.class);
            log.info("Response: " + econtractResponse.getBody());

            //convert to json
            XmlMapper xmlMapper = new XmlMapper();
            JsonNode res = xmlMapper.readTree(econtractResponse.getBody()).path("Body");

            response = ResponseDTO.builder()
                .header(header)
                .body(res)
                .build();
        }
        catch (HttpClientErrorException exception){
            log.error(exception);

            XmlMapper errorMapper = new XmlMapper();
            try {
                JsonNode errorBody = null;
                if(!exception.getResponseBodyAsString().isEmpty()){
                    errorBody = errorMapper.readTree(exception.getResponseBodyAsString());
                }

                ErrorBodyDTO error = ErrorBodyDTO.builder()
                    .status(exception.getStatusCode().toString())
                    .errorBody(errorBody)
                    .build();
                response = ResponseDTO.builder()
                    .header(header)
                    .body(error)
                    .build();
            }
            catch (Exception e){
                log.error("exception: " + e);
            }
        }
        catch(Exception e){
            log.error("exception: " + e);
        }
        return response;
    }
}
