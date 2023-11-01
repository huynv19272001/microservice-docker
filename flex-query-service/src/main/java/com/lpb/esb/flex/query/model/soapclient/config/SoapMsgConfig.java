/**
 * @author Trung.Nguyen
 * @date 21-Apr-2022
 * */
package com.lpb.esb.flex.query.model.soapclient.config;

public class SoapMsgConfig {

    /**
     * FCUBSInternalService
     * */
	private static final String BEGIN_FCUBS_REQUEST = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:fcub=\"http://fcubs.ofss.com/service/FCUBSInternalService\">"
											        + "<soapenv:Header/>"
											        + "<soapenv:Body>"
											        + "<fcub:INTERNALSERVICE_REQ>";

	private static final String ENDING_FCUBS_REQUEST = "</fcub:INTERNALSERVICE_REQ>"
											         + "</soapenv:Body>"
											         + "</soapenv:Envelope>";

	public static final String FCUBS_REQ_HEADER_TMP = "<FCUBS_HEADER>"
													+ "<MSGID>%s</MSGID>"
													+ "<USERID>%s</USERID>"
													+ "<BRANCH>001</BRANCH>"
													+ "<SERVICE>%s</SERVICE>"
													+ "<OPERATION>%s</OPERATION>"
													+ "</FCUBS_HEADER>";

	public static final String GET_ACCOUNT_LIST_REQ_BODY_TMP = "<FCUBS_BODY>"
											  	  			 + "<Get_Account_List_IO>"
											  	  			 + "<ACCOUNT_NUMBER>%s</ACCOUNT_NUMBER>"
											  	  			 + "<ACCOUNT_TYPE>%s</ACCOUNT_TYPE>"
											  	  			 + "<RECORD_PER_PAGE>100</RECORD_PER_PAGE>"
											  	  			 + "<PAGE_NUMBER>1</PAGE_NUMBER>"
											  	  			 + "</Get_Account_List_IO>"
											  	  			 + "</FCUBS_BODY>";

	public static final String GET_CUSTOMER_LIST_REQ_BODY_TMP = "<FCUBS_BODY>"
	  			 											  + "<Get_Customer_List_IO>"
	  			 											  + "<CUSTOMER_NUMBER>%s</CUSTOMER_NUMBER>"
	  			 											  + "<UNIQUE_ID_VALUE>%s</UNIQUE_ID_VALUE>"
	  			 											  + "<RECORD_PER_PAGE>100</RECORD_PER_PAGE>"
	  			 											  + "<PAGE_NUMBER>1</PAGE_NUMBER>"
	  			 											  + "</Get_Customer_List_IO>"
	  			 											  + "</FCUBS_BODY>";

    /**
     * SMSCUserInt
     * */
    private static final String BEGIN_SMSCU_REQUEST = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sms=\"http://lienvietbank.net/SMSCUserInt/\">"
                                                    + "<soapenv:Header/>";

    private static final String ENDING_SMSCU_REQUEST = "</soapenv:Envelope>";

    public static final String GET_ACCOUNT_DETAIL_LIST_REQ_BODY_TMP = "<soapenv:Body>"
                                                                    + "<sms:SearchCoreAccountRequest>"
                                                                    + "<MsgBody>"
                                                                    + "<SearchCoreAccountDetail>"
                                                                    + "<SystemID></SystemID>"
                                                                    + "<CustomerNo></CustomerNo>"
                                                                    + "<AccountNo>%s</AccountNo>"
                                                                    + "<PhoneNo></PhoneNo>"
                                                                    + "<UniqueID></UniqueID>"
                                                                    + "<UniqueValue></UniqueValue>"
                                                                    + "<AccountPerPage>50</AccountPerPage>"
                                                                    + "<PageNumber>1</PageNumber>"
                                                                    + "<SearchType>1</SearchType>"
                                                                    + "</SearchCoreAccountDetail>"
                                                                    + "</MsgBody>"
                                                                    + "</sms:SearchCoreAccountRequest>"
                                                                    + "</soapenv:Body>";

	public static String buildFCubsRequest(String header, String body) {

		if ((header == null) || (header.trim().equals("")))
			return null;
		if ((body == null) || (body.trim().equals("")))
			return null;

		StringBuilder sb = new StringBuilder();
		sb.append(BEGIN_FCUBS_REQUEST);
		sb.append(header);
		sb.append(body);
		sb.append(ENDING_FCUBS_REQUEST);
		return sb.toString();
	}

    public static String buildSmsCURequest(String body) {

        if ((body == null) || (body.trim().equals("")))
            return null;

        StringBuilder sb = new StringBuilder();
        sb.append(BEGIN_SMSCU_REQUEST);
        sb.append(body);
        sb.append(ENDING_SMSCU_REQUEST);
        return sb.toString();
    }

}
