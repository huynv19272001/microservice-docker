/**
 * @author Trung.Nguyen
 * @date 21-Apr-2022
 * */
package com.lpb.esb.flex.query.model.soapclient;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.lpb.esb.flex.query.model.data.LpbAccount;
import com.lpb.esb.flex.query.model.data.LpbAccountInfo;
import com.lpb.esb.flex.query.model.soapclient.config.SoapMsgConfig;
import com.lpb.esb.flex.query.model.soapclient.config.SoapURLConfig;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.model.response.ResponseModel;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class GetAccountListWSClient extends FlexSoapWSClient {
	
	@Autowired
	SoapURLConfig soapURLConfig;
	
	public ResponseModel<List<LpbAccount>> findAccountInfo(LpbAccountInfo accountInfo) {
		String url = soapURLConfig.getFlexInquiryUri();
		ResponseModel<List<LpbAccount>> response = new ResponseModel<>();
		LpbResCode resCode = new LpbResCode();
		resCode.setErrorCode("99");
		resCode.setErrorDesc("Loi khong xac dinh");
		response.setResCode(resCode);
		try {
			// Create a SOAP request based on input parameters
			String header = String.format(SoapMsgConfig.FCUBS_REQ_HEADER_TMP, "?", "?", "FCUBSInternalService", "GetAccountList");
			String body = String.format(SoapMsgConfig.GET_ACCOUNT_LIST_REQ_BODY_TMP, accountInfo.getAccountNumber(), accountInfo.getAccountType());
			String soapReq = SoapMsgConfig.buildFCubsRequest(header, body);
			log.info("search-account, request: "+ soapReq);
			String soapRes = this.callWSSoap(url, soapReq);
			log.info("search-account, response: "+ soapRes);
			
			if ((soapRes == null) || (soapRes.trim().isEmpty())) return response;
			
			// Get XML Document Builder
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			// Process XML securely, avoid attacks like XML External Entities (XXE)
			docBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			// Build XML Document
			Document document = docBuilder.parse(new InputSource(new StringReader(soapRes)));
			if (document == null) return null;
			// Normalize the XML Structure
			document.getDocumentElement().normalize();
			
			// Get node list
			NodeList nList = document.getElementsByTagName("ACCOUNT");
			if ((nList == null) || (nList.getLength() <= 0)) {
				resCode.setErrorCode("01");
	        	resCode.setErrorDesc("Khong tim thay thong tin tai khoan");
	        	return response;
			}
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        
	        // Create a new XmlMapper to read XML tags
	     	XmlMapper xmlMapper = new XmlMapper();
	        // Create a account list
	     	List<LpbAccount> accountList = new ArrayList<LpbAccount>();
	        for (int i = 0; i < nList.getLength(); i++) {
	            Node nNode = nList.item(i);
	            // Create a StringWriter to read XML node
	            StringWriter writer = new StringWriter();
	            transformer.transform(new DOMSource(nNode), new StreamResult(writer));
	            // Write XML structure to string
	            String xmlAccount = writer.toString();
	            // Parse XML string into object
	            LpbAccount account = xmlMapper.readValue(xmlAccount, LpbAccount.class);
	            // Add account into list
	            if(account != null) accountList.add(account);
	        }
	        if(accountList.size() > 0) {
	        	response.setData(accountList);
	        	resCode.setErrorCode("00");
	        	resCode.setErrorDesc("Thanh cong");
	        	return response;
	        } else {
	        	resCode.setErrorCode("01");
	        	resCode.setErrorDesc("Khong tim thay thong tin tai khoan");
	        	return response;
	        }
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
			resCode.setErrorDesc(ex.getMessage());
			return response;
		} catch (SAXException ex) {
			ex.printStackTrace();
			resCode.setErrorDesc(ex.getMessage());
			return response;
		} catch (TransformerException ex) {
			ex.printStackTrace();
			resCode.setErrorDesc(ex.getMessage());
			return response;
		} catch (IOException ex) {
			ex.printStackTrace();
			resCode.setErrorDesc(ex.getMessage());
			return response;
		}
	}

}
