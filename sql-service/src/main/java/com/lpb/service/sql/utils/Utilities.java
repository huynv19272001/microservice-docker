package com.lpb.service.sql.utils;

import com.lpb.service.sql.utils.constants.EnumTypeField;
import lombok.extern.log4j.Log4j2;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.Pattern;

@Log4j2
public class Utilities {

    private static final String FORMAT_DDMMYYY = "dd/MM/yyyy";
    private static final String FORMAT_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";
    private static final String FORMAT_DDMMYYYYHHMMSS_ = "dd-MM-yyyy HH:mm:ss";
    private static final String FORMAT_DDMONRR = "dd-MMM-yy";

    public static Date convertStrToSQLDate(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DDMMYYY);
        dateFormat.setLenient(false);
        java.util.Date utilDate;
        Date sqlDate;

        try {
            utilDate = dateFormat.parse(strDate);
            sqlDate = new Date(utilDate.getTime());
            return sqlDate;
        } catch (ParseException e) {
            log.error("EXCEPTION convertStrToSQLDate: " + e.getMessage());
            return null;
        }
    }

    public static Date convertStrtoSQLDateTime(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            FORMAT_DDMMYYYYHHMMSS);
        java.util.Date utilDate;
        Date sqlDate;

        try {
            utilDate = dateFormat.parse(strDate);
            sqlDate = new Date(utilDate.getTime());
            return sqlDate;
        } catch (ParseException e) {
            log.error("Exception ParseException: " + e.getMessage());
            return null;
        }

    }

    public static String convertSqlDateToStr(Date sqlDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DDMMYYY);
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        return dateFormat.format(utilDate);
    }

    public static String convertSqlTimeStampToStr(Timestamp sqlDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DDMMYYY);
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        return dateFormat.format(utilDate);
    }

    public static String convertSqlTimeStamptoStrHHmmss(
        Timestamp sqlDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            FORMAT_DDMMYYYYHHMMSS_);
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
        return dateFormat.format(utilDate);
    }

    public static String convertOracleSqlDateToStr(oracle.sql.DATE oraDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DDMMYYY);
        Timestamp timestampValue = oraDate.timestampValue();
        java.util.Date utilDate = new java.util.Date(timestampValue.getTime());
        return dateFormat.format(utilDate);

    }

    public static Timestamp convertStrToSqlTimeStamp(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            FORMAT_DDMMYYYYHHMMSS);

        try {
            java.util.Date utilDate = dateFormat.parse(dateStr);
            Timestamp timeStamp = new Timestamp(utilDate.getTime());
            return timeStamp;
        } catch (ParseException e) {
            log.error("STR to TIMESTAMP EX: " + e.getMessage());
            return null;
        }

    }

    public static String convertDateCoretoStr(String dateCore) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DDMONRR);
        try {
            java.util.Date utilDt = dateFormat.parse(dateCore);
            dateFormat = new SimpleDateFormat(FORMAT_DDMMYYY);
            return dateFormat.format(utilDt);
        } catch (Exception ex) {
            log.error("Format Date core ex: " + ex.getMessage());
            return null;
        }
    }

    /**
     * GENERATE OTP
     */
    public static String generateOTP(int lengthOTP) {
        return new String(OTP(lengthOTP));

    }

    static char[] OTP(int len) {
        log.error("Start generate OTP");
        // Using numeric values
        String numbers = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // Using random method
        Random rndm_method = new Random();
        char[] otp = new char[len];
        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        log.error("end generate OTP");
        return otp;
    }

    /**
     * REMOVE VIETNAMES UNICODE
     */
    public static String convertStringToURL(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern
                .compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").replaceAll("đ", "d")
                .replaceAll("Đ", "D");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertClobToStr(java.sql.Clob clob) {
        try {
            StringBuilder sb = new StringBuilder((int) clob.length());
            Reader r = clob.getCharacterStream();
            char[] cbuf = new char[2048];
            int n;
            while ((n = r.read(cbuf, 0, cbuf.length)) != -1) {
                sb.append(cbuf, 0, n);
            }
            return sb.toString();
        } catch (Exception ex) {
            log.error("Exception Clob to Str: " + ex.getMessage());
            return null;
        }
    }

    public static String convertObjSelectToStr(Object obj, String valType) {
        if (obj != null) {
            if (obj instanceof Date) {
                return Utilities.convertSqlDateToStr((Date) obj);
            } else if (obj instanceof Timestamp) {
                return Utilities
                    .convertSqlTimeStampToStr((Timestamp) obj);
            } else if (obj instanceof Clob){
                return Utilities
                    .convertClobToStr((Clob) obj);
            }
            else {
                String val = obj.toString();
                if (valType.trim().toUpperCase()
                    .equals(EnumTypeField.DATE.toString().toUpperCase())) {
                    val = Utilities.convertDateCoretoStr(obj.toString());
                } else {
                    val = obj.toString();
                }

                return val;
            }
        } else {
            return "";
        }
    }

}
