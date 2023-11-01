package com.lpb.esb.service.sms.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by tudv1 on 2022-07-08
 */
class EncryptMsgVtUtilsTest {
    EncryptMsgVtUtils encryptMsgVtUtils;

    @BeforeEach
    void setUp() {
        encryptMsgVtUtils = new EncryptMsgVtUtils();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void encryptMessage() throws Exception {
        String priCP = "MIGEAgEAMBAGByqGSM49AgEGBSuBBAAKBG0wawIBAQQgy7gCMkrwiGowI6O/KGwYtgVTneahwlbLFVByzgzdCCmhRANCAART0e1HmU9GYiYSvb+S58i3kv8sBupjKuq8jcJauueh+C27pwWcmQpZk4wLjYPI5cPXF9w3GTGyiqyk6CWwQr6T";
        String pubVT = "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEe9YVVfiuEbTC8MLFcmettM9z6i1bh49kM97NZJ0yLCZcDQtWhuQ230W/nackSySWO8tErAzDWiMdvEACaHevaA==";
        String initVec = "Banksms@Viettel!";
        String msg = "tudv test";
        String encryptedMsg = encryptMsgVtUtils.encryptMessage(msg, initVec, priCP, pubVT);
        System.out.println(encryptedMsg);
    }
}
