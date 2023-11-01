package com.lpb.service.bidv.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.service.bidv.model.response.BIDVResponse;
import com.lpb.service.bidv.repositories.EsbBIDVTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Utils {
    @Autowired
    SHA256withRSA SHA256withRSA;

    public static String getMacAddress() throws Exception {
        String macAddress = null;
        String command = "ifconfig";

        String osName = System.getProperty("os.name");

        if (osName.startsWith("Windows")) {
            command = "ipconfig /all";
        } else if (osName.startsWith("Linux") || osName.startsWith("Mac") || osName.startsWith("HP-UX")
            || osName.startsWith("NeXTStep") || osName.startsWith("Solaris") || osName.startsWith("SunOS")
            || osName.startsWith("FreeBSD") || osName.startsWith("NetBSD")) {
            command = "ifconfig -a";
        } else if (osName.startsWith("OpenBSD")) {
            command = "netstat -in";
        } else if (osName.startsWith("IRIX") || osName.startsWith("AIX") || osName.startsWith("Tru64")) {
            command = "netstat -ia";
        } else if (osName.startsWith("Caldera") || osName.startsWith("UnixWare") || osName.startsWith("OpenUNIX")) {
            command = "ndstat";
        } else {// Note: Unsupported system.
            throw new Exception("The current operating system '" + osName + "' is not supported.");
        }

        Process pid = Runtime.getRuntime().exec(command);
        BufferedReader in = new BufferedReader(new InputStreamReader(pid.getInputStream()));
        Pattern p = Pattern.compile("([\\w]{1,2}(-|:)){5}[\\w]{1,2}");
        while (true) {
            String line = in.readLine();
            if (line == null)
                break;

            Matcher m = p.matcher(line);
            if (m.find()) {
                macAddress = m.group();
                break;
            }
        }
        in.close();
        return macAddress;
    }

    public Boolean verify(BIDVResponse responseCT001, List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(responseCT001.getData());
        return SHA256withRSA.verify(serviceInfo.get(0).getUDF3(), data, responseCT001.getSignature());
//        return SHA256withRSA.verify("C:\\Users\\tondd1\\Downloads\\bidv\\API Webservice LBP\\API Webservice LBP\\BIDV.cer", data, responseCT001.getSignature());
    }

    public Boolean verify(BIDVResponse responseCT001, List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo, String type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(responseCT001.getData());
        return SHA256withRSA.verify(serviceInfo.get(0).getUDF3(), data, responseCT001.getSignature(), type);
//        return SHA256withRSA.verify("C:\\Users\\tondd1\\Downloads\\bidv\\API Webservice LBP\\API Webservice LBP\\BIDV.cer", data, responseCT001.getSignature());
    }

    public String getAccessToken(List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo) throws Exception {
        String accessToken = serviceInfo.get(0).getUDF5() + "@@" + Utils.getMacAddress();
        return accessToken;
    }

    public String getSignature(String data, List<EsbBIDVTransactionRepository.ServiceInfo> serviceInfo) throws Exception {
        String signature = SHA256withRSA.bytesToString
            (SHA256withRSA.makeSignature(data, serviceInfo.get(0).getUDF2(), serviceInfo.get(0).getUDF1()));
//        String signature = SHA256withRSA.bytesToString
//            (SHA256withRSA.makeSignature(data, "C:\\Users\\tondd1\\Downloads\\bidv\\LPB\\LPB\\lpb-bidv.pfx", serviceInfo.get(0).getUDF1()));
        return signature;
    }
}
