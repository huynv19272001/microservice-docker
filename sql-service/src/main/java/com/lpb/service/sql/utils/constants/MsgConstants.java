package com.lpb.service.sql.utils.constants;


/**
 * CONNSTANT FOR ESB
 * USER/PASS HEADER GATEWAY
 * @author HUNGLV5
 *
 */
public abstract class MsgConstants {
	public static final String XPATH_HTTPInputHeader = "HTTPInputHeader/X-Remote-Addr";
	public static final String XPATH_HTTPInputHeader_F5 = "HTTPInputHeader/X-Forwarded-For";
	public static final String ROUTE_TO_LABEL = "Destination/RouterList/DestinationData/labelName";
	public static final String ESB_SUCCESS = "ESB-000";
	public static final String ESB_FAIL_ROUTING = "ESB-080";
	public static final String ESB_TIMEOUT ="ESB-090";
	public static final String ESB_USER_GATEWAY="ESB_PAYMENT";
	public static final String ESB_PASS_GATEWAY="lMjZ30djFY9F5Ut9EkkOsKLDyfVLmZfQe0kNxZCT5ScLEDNuoeHVWm7t0Bm1iLudqAYwLZXyUlnZFJDefp500MghJzXd/edBV0PC/AYy0iR388oR8NNLzJDMl2Xpi1W5VkR4X04YiiareCdzm99neKJHtsBsFS5rTAshqhxMcPc=";
	public static final String XPATH_HTTPInputHeader_F5_PARTNER = "HTTPInputHeader/X-Forwarded-For[2]";


}
