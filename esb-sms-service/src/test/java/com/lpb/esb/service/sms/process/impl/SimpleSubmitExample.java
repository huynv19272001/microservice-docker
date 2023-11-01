/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.lpb.esb.service.sms.process.impl;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author uudashr
 */
public class SimpleSubmitExample {
    private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();
    ;
    private static final byte[] EMPTY_ARRAY = new byte[]{};

    public static void main(String[] args) throws UnsupportedEncodingException {
        SMPPSession session = new SMPPSession();
        try {
            session.connectAndBind("localhost", 8056, new BindParameter(BindType.BIND_TX, "test", "test1233", "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
        } catch (IOException e) {
            System.err.println("Failed connect and bind to host");
            e.printStackTrace();
        }
        String msg = "Tài liệu mô tả chi tiết các thông số kết nối thông qua các API sử dụng WebSevice. Tài liệu mô tả chi tiết khuôn dạng dữ liệu của các message tham gia quá trình truyền nhận giữa NHTM và hệ thống của Tổng cục Thuế. Tài liệu này được dùng làm đầu vào cho các quá trình lập trình, kết nối, system test và UAT của việc xây dựng chương trình";
        try {
            OptionalParameter messagePayloadParameter;
            messagePayloadParameter =
                    new OptionalParameter.OctetString(
                            OptionalParameter.Tag.MESSAGE_PAYLOAD.code(),
                            msg,
                            "UTF-8");
            String messageId = session.submitShortMessage("smpp"
                    , TypeOfNumber.INTERNATIONAL
                    , NumberingPlanIndicator.UNKNOWN
                    , "1616"
                    , TypeOfNumber.INTERNATIONAL
                    , NumberingPlanIndicator.UNKNOWN
                    , "628176504657"
                    , new ESMClass()
                    , (byte) 0
                    , (byte) 1
                    , timeFormatter.format(new Date())
                    , null
                    , new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT)
                    , (byte) 0
                    , new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false)
                    , (byte) 0
                    , "Tài liệu mô tả chi tiết các thông số kết nối thông qua các API sử dụng WebSevice".getBytes()
//                    , EMPTY_ARRAY
//                    , messagePayloadParameter
            );
            System.out.println("Message submitted, message_id is " + messageId);
        } catch (PDUException e) {
            // Invalid PDU parameter
            System.err.println("Invalid PDU parameter");
            e.printStackTrace();
        } catch (ResponseTimeoutException e) {
            // Response timeout
            System.err.println("Response timeout");
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            // Invalid response
            System.err.println("Receive invalid respose");
            e.printStackTrace();
        } catch (NegativeResponseException e) {
            // Receiving negative response (non-zero command_status)
            System.err.println("Receive negative response");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO error occur");
            e.printStackTrace();
        }

        session.unbindAndClose();
    }


}
