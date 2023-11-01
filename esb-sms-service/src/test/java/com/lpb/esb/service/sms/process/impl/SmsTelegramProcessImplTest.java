package com.lpb.esb.service.sms.process.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by tudv1 on 2021-08-25
 */
public class SmsTelegramProcessImplTest {


    @Before
    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sendMessage() throws JsonProcessingException {
        String raw = "{1:F01LVBKVNVXAXXX1111111111}{2:I103CHASJPJTXXXXN}{3:{108:100MSOG22143035T}}{4:\n" +
            ":20:100OFIN221436001\n" +
            ":23B:CRED\n" +
            ":32A:220523JPY5000000,\n" +
            ":50K:/052989420001\n" +
            "1/PHAM THI LAN ANH\n" +
            "2/LUONG BANG, KIM DONG, HUNG YEN\n" +
            "3/VIET NAM\n" +
            ":57A:BOTKJPJT\n" +
            ":59:/0180423\n" +
            "PHAM QUOC DAT\n" +
            "TOKYO, JAPAN\n" +
            ":70:CHUYEN TIEN\n" +
            ":71A:SHA\n" +
            "-}*************************";
        String[] list = raw.split("}\\{");
        for (String s : list) {
            System.out.println("line: "+s);
        }
    }
}
