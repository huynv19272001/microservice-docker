/**
 * @author Trung.Nguyen
 * @date 05-May-2022
 * */
package com.lpb.esb.etax.inquiry.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class FlexCubsUtils {

	public String getMsgIdByDate() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
			String msgId = sdf.format(new Timestamp(System.currentTimeMillis()));
			return msgId;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String getCurrentDateTime(String format) {
		try {
			if (format == null || format.trim().isEmpty()) format = "dd-MM-yyyy HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String msgId = sdf.format(new Timestamp(System.currentTimeMillis()));
			return msgId;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
