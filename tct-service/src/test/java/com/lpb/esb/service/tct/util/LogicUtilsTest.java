package com.lpb.esb.service.tct.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lpb.esb.service.tct.util.cert.XMLSigner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by tudv1 on 2022-02-28
 */
public class LogicUtilsTest {
    LogicUtils logicUtils;

    @Before
    public void setUp() throws Exception {
        logicUtils = new LogicUtils();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void convertXmlToJson() throws Exception {
        String input = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><ns:getListUserResponse xmlns:ns=\"http://esmac.ewallet.lpb.com\"><ns:return xsi:type=\"ax21:ListUserResponse\" xmlns:ax218=\"http://account.entity.ewallet.lpb.com/xsd\" xmlns:ax216=\"http://customer.entity.ewallet.lpb.com/xsd\" xmlns:ax227=\"http://SMSCUserInt.lienvietbank.net/xsd\" xmlns:ax239=\"http://product.entity.ewallet.lpb.com/xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ax214=\"http://user.entity.ewallet.lpb.com/xsd\" xmlns:ax28=\"http://transaction.entity.ewallet.lpb.com/xsd\" xmlns:ax27=\"http://entity.ewallet.lpb.com/xsd\" xmlns:ax223=\"http://database.entity.ewallet.lpb.com/xsd\" xmlns:ax234=\"http://OTP.ewallet.lpb.com/xsd\" xmlns:ax232=\"http://jpa.entity.ewallet.lpb.com/xsd\" xmlns:ax23=\"http://request.ewallet.lpb.com/xsd\" xmlns:ax25=\"http://common.entity.ewallet.lpb.com/xsd\" xmlns:ax21=\"http://response.ewallet.lpb.com/xsd\"><ax21:resultCode>0</ax21:resultCode><ax21:resultDesc>Tìm kiếm thành công</ax21:resultDesc><ax21:user xsi:type=\"ax223:MasterUser\"><ax223:addressLine xsi:nil=\"true\"/><ax223:approveStatus>N</ax223:approveStatus><ax223:authStat>N</ax223:authStat><ax223:autoAuth xsi:nil=\"true\"/><ax223:checkerDate>2016-01-17T17:08:38.000+07:00</ax223:checkerDate><ax223:checkerId>ADMIN</ax223:checkerId><ax223:custId>20174</ax223:custId><ax223:custNo>100020944</ax223:custNo><ax223:dateOfBirth xsi:nil=\"true\"/><ax223:dateOfIssue xsi:nil=\"true\"/><ax223:debugState>-1</ax223:debugState><ax223:email2 xsi:nil=\"true\"/><ax223:email3 xsi:nil=\"true\"/><ax223:endDate xsi:nil=\"true\"/><ax223:forcePwdChange xsi:nil=\"true\"/><ax223:fullName>Bùi Văn Thành</ax223:fullName><ax223:language xsi:nil=\"true\"/><ax223:lastModifiedOn xsi:nil=\"true\"/><ax223:lastPwdChangedOn xsi:nil=\"true\"/><ax223:lastSignedOn xsi:nil=\"true\"/><ax223:lastStatusChangedOn xsi:nil=\"true\"/><ax223:limitCcy xsi:nil=\"true\"/><ax223:limitType xsi:nil=\"true\"/><ax223:loginFailCount>0</ax223:loginFailCount><ax223:loginState xsi:nil=\"true\"/><ax223:makerDate>2016-01-17T17:08:38.000+07:00</ax223:makerDate><ax223:makerId>ADMIN</ax223:makerId><ax223:maxAmtDaily xsi:nil=\"true\"/><ax223:maxAmtPerTxn xsi:nil=\"true\"/><ax223:maxAuthDaily xsi:nil=\"true\"/><ax223:maxTxnDaily xsi:nil=\"true\"/><ax223:media xsi:nil=\"true\"/><ax223:mobilePhone2 xsi:nil=\"true\"/><ax223:mobilePhone3 xsi:nil=\"true\"/><ax223:modNo xsi:nil=\"true\"/><ax223:needChangePass>Y</ax223:needChangePass><ax223:nextPwdChangedOn>2020-04-21</ax223:nextPwdChangedOn><ax223:nickName xsi:nil=\"true\"/><ax223:onceAuth xsi:nil=\"true\"/><ax223:placeId xsi:nil=\"true\"/><ax223:placeOfIssue xsi:nil=\"true\"/><ax223:primaryEmail xsi:nil=\"true\"/><ax223:primaryPhone>0914008188</ax223:primaryPhone><ax223:primaryUser>Y</ax223:primaryUser><ax223:pwdReset>1</ax223:pwdReset><ax223:sex xsi:nil=\"true\"/><ax223:startDate>2016-01-17T17:08:38.000+07:00</ax223:startDate><ax223:tokenType xsi:nil=\"true\"/><ax223:uniqueId>1</ax223:uniqueId><ax223:uniqueValue>013332965</ax223:uniqueValue><ax223:userGroup xsi:nil=\"true\"/><ax223:userId>20271</ax223:userId><ax223:userName>0914008188</ax223:userName><ax223:userPwd>5fbeacc4b3acf065a9d5b87de8def3ce01326f1f21715988c0d95bd638354018</ax223:userPwd><ax223:userRole xsi:nil=\"true\"/><ax223:userSalt>9C1D2X</ax223:userSalt><ax223:userStatus>A</ax223:userStatus><ax223:userType>1</ax223:userType></ax21:user></ns:return></ns:getListUserResponse></soapenv:Body></soapenv:Envelope>";
//        javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
//
//        dbf.setNamespaceAware(false);
//        dbf.setIgnoringElementContentWhitespace(true);
//        dbf.setValidating(false);
//        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
//        Document doc = dbf.newDocumentBuilder().parse(inputStream);
//        System.out.println(XMLSigner.getStringFromDocument(doc));

        String xml1 = removeXmlStringNamespaceAndPreamble(input);
        System.out.println(xml1);
        JSONObject jsonObject = XML.toJSONObject(
            xml1
//                .replaceAll("xsi:nil=\"true\"", "")
        );

        System.out.println(jsonObject.toString());
    }

    public static String removeXmlStringNamespaceAndPreamble(String xmlString) {
        return xmlString.replaceAll("(<\\?[^<]*\\?>)?", "") /* remove preamble */
            .replaceAll("xmlns.*?(\"|\').*?(\"|\')", "") /* remove xmlns declaration */
            .replaceAll("xsi.*?(\"|\').*?(\"|\')", "") /* remove xmlns declaration */
            .replaceAll("(<)(\\w+:)(.*?>)", "$1$3") /* remove opening tag prefix */
            .replaceAll("(</)(\\w+:)(.*?>)", "$1$3"); /* remove closing tags prefix */
    }

    @Test
    public void convertJsonToXml() {
        String json = "{\"esb_header\":{\"msg_id\":\"{{$randomUUID}}\",\"service_id\":\"090002\",\"product_code\":\"SEND_BANCHNAME_OTP\",\"has_role\":\"FPT_SMS_ALL\"},\"esb_body\":{\"mobile_number\":\"84396581996\",\"content\":\"132132313\",\"service_log\":1,\"category\":\"OTP_EBANK\"}}";
        System.out.println(logicUtils.convertJsonToXml(json));
    }

    @Test
    public void parseDataFromXml() {
        String xml = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
            "   <S:Body>\n" +
            "      <ns0:xuLyTruyVanMsgResponse xmlns:ns0=\"http://gip.response.com/\">\n" +
            "         <return><![CDATA[<DATA>\n" +
            "   <HEADER>\n" +
            "      <VERSION>1</VERSION>\n" +
            "      <SENDER_CODE>GIP</SENDER_CODE>\n" +
            "      <SENDER_NAME>Hệ Thống GIP</SENDER_NAME>\n" +
            "      <RECEIVER_CODE>01357005</RECEIVER_CODE>\n" +
            "      <RECEIVER_NAME>01357005</RECEIVER_NAME>\n" +
            "      <TRAN_CODE>05011</TRAN_CODE>\n" +
            "      <MSG_ID>GIP202203093336673</MSG_ID>\n" +
            "      <MSG_REFID/>\n" +
            "      <ID_LINK/>\n" +
            "      <SEND_DATE>03-09-2022</SEND_DATE>\n" +
            "      <ORIGINAL_CODE>GIP</ORIGINAL_CODE>\n" +
            "      <ORIGINAL_NAME>Hệ Thống GIP</ORIGINAL_NAME>\n" +
            "      <ORIGINAL_DATE>03-09-2022</ORIGINAL_DATE>\n" +
            "      <ERROR_CODE/>\n" +
            "      <ERROR_DESC/>\n" +
            "      <SPARE1/>\n" +
            "      <SPARE2/>\n" +
            "      <SPARE3/>\n" +
            "   </HEADER>\n" +
            "   <BODY>\n" +
            "      <ROW>\n" +
            "         <TYPE>00047</TYPE>\n" +
            "         <NAME>Kết quả truy vấn sổ thuế LPTB</NAME>\n" +
            "         <THONGTIN_NNT>\n" +
            "            <THONGTINCHUNG>\n" +
            "               <ROW_NNT>\n" +
            "                  <MST>0108937704</MST>\n" +
            "                  <TEN_NNT>CÔNG TY TNHH THƯƠNG MẠI VÀ DỊCH VỤ QUANG MINH ĐỨC</TEN_NNT>\n" +
            "                  <LOAI_NNT>0100</LOAI_NNT>\n" +
            "                  <SO_CMND/>\n" +
            "                  <CHUONG>755</CHUONG>\n" +
            "                  <MA_CQT_QL>1054817</MA_CQT_QL>\n" +
            "               </ROW_NNT>\n" +
            "            </THONGTINCHUNG>\n" +
            "            <DIACHI>\n" +
            "               <ROW_DIACHI>\n" +
            "                  <MOTA_DIACHI>Xóm Tân Lập, Cụm 13, Vĩnh Ninh, Xã Vĩnh Quỳnh</MOTA_DIACHI>\n" +
            "                  <MA_TINH>01TTT</MA_TINH>\n" +
            "                  <MA_HUYEN>020HH</MA_HUYEN>\n" +
            "                  <MA_XA>1012313</MA_XA>\n" +
            "               </ROW_DIACHI>\n" +
            "            </DIACHI>\n" +
            "            <SOTHUE>\n" +
            "               <ROW_SOTHUE>\n" +
            "                  <MST>0108937704</MST>\n" +
            "                  <MA_CHUONG>757</MA_CHUONG>\n" +
            "                  <MA_CQ_THU>1054817</MA_CQ_THU>\n" +
            "                  <SHKB>0018</SHKB>\n" +
            "                  <MA_TMUC>2802</MA_TMUC>\n" +
            "                  <NO_CUOI_KY>29220000</NO_CUOI_KY>\n" +
            "                  <SO_TAI_KHOAN_CO>7111</SO_TAI_KHOAN_CO>\n" +
            "                  <SO_QDINH>11020220003048391</SO_QDINH>\n" +
            "                  <NGAY_QDINH>25/02/2022</NGAY_QDINH>\n" +
            "                  <LOAI_TIEN>VND</LOAI_TIEN>\n" +
            "                  <TI_GIA/>\n" +
            "                  <LOAI_THUE>12</LOAI_THUE>\n" +
            "                  <DIA_CHI_TS/>\n" +
            "                  <SO_KHUNG>RLUA741BBNN073134</SO_KHUNG>\n" +
            "                  <SO_MAY>G4LCMG046753</SO_MAY>\n" +
            "                  <MA_DBHC>020HH</MA_DBHC>\n" +
            "                  <KY_THUE>03/2022</KY_THUE>\n" +
            "                  <DAC_DIEM_PTIEN>LoạiTS:Ô tô con; Nhãn hiệu:HYUNDAI; Số loại/Tên TM:ACCENT 1.4 AT PE; Số BKS:</DAC_DIEM_PTIEN>\n" +
            "               </ROW_SOTHUE>\n" +
            "            </SOTHUE>\n" +
            "         </THONGTIN_NNT>\n" +
            "      </ROW>\n" +
            "   </BODY>\n" +
            "</DATA>]]></return>\n" +
            "      </ns0:xuLyTruyVanMsgResponse>\n" +
            "   </S:Body>\n" +
            "</S:Envelope>";
        System.out.println(logicUtils.parseDataFromXml(xml).toMap());
    }

    @Test
    public void recursiveJsonKeyConverterToLower() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DATA><HEADER><VERSION>1</VERSION><SENDER_CODE>GIP</SENDER_CODE><SENDER_NAME>Hệ Thống GIP</SENDER_NAME><RECEIVER_CODE>DVCQG</RECEIVER_CODE><RECEIVER_NAME>Dịch vụ công Quốc gia</RECEIVER_NAME><TRAN_CODE>05011</TRAN_CODE><MSG_ID>GIP202108060051854</MSG_ID><MSG_REFID/><ID_LINK/><SEND_DATE>08-06-2021</SEND_DATE><ORIGINAL_CODE>GIP</ORIGINAL_CODE><ORIGINAL_NAME>Hệ Thống GIP</ORIGINAL_NAME><ORIGINAL_DATE>08-06-2021</ORIGINAL_DATE><ERROR_CODE/><ERROR_DESC/><SPARE1/><SPARE2/><SPARE3/></HEADER><BODY><ROW><TYPE>00110</TYPE><NAME>Trả lời truy vấn sổ thuế nhà đất</NAME><SOTHUE><TRANGTHAI>01</TRANGTHAI><MOTA_TRANGTHAI>Tồn tại mã hồ sơ và số CMND/CCCD và có còn nợ thuế đất</MOTA_TRANGTHAI><TONG_TIEN><MA_SO_THUE>8587448068</MA_SO_THUE><TT_NNT>4634000</TT_NNT><ROW_SOTHUE><MA_HS>000.00.32.H19-210708-0032</MA_HS><MA_GCN/><SO_THUADAT>35</SO_THUADAT><SO_TO_BANDO>45</SO_TO_BANDO><DIA_CHI_TS>xã Đại Phước, huyện Nhơn Trạch, tỉnh Đồng Nai</DIA_CHI_TS><MA_TINH_TS>713</MA_TINH_TS><TEN_TINH_TS>Đồng Nai</TEN_TINH_TS><MA_HUYEN_TS>71317</MA_HUYEN_TS><TEN_HUYEN_TS>Huyện Nhơn Trạch</TEN_HUYEN_TS><MA_XA_TS>7131703</MA_XA_TS><TEN_XA_TS>Xã Đại Phước</TEN_XA_TS><MA_DBHC>26476</MA_DBHC><MA_SO_THUE>8587448068</MA_SO_THUE><TEN_NNT>Lê Thị Huệ</TEN_NNT><SO>270093701</SO><MOTA_DIACHI>11A,Khu phố 6, P.Thống Nhất,TP. Biên Hòa, Đồng Nai</MOTA_DIACHI><MA_TINH>713</MA_TINH><TEN_TINH>Đồng Nai</TEN_TINH><MA_HUYEN>71301</MA_HUYEN><TEN_HUYEN>Thành phố Biên Hoà</TEN_HUYEN><MA_XA>7130133</MA_XA><TEN_XA>Phường Thống Nhất</TEN_XA><SO_QUYET_DINH>LTB2171317-TK0029952/TB-CCT</SO_QUYET_DINH><NGAY_QUYET_DINH>2021-07-20 08:05:25.0</NGAY_QUYET_DINH><MA_CHUONG>757</MA_CHUONG><MA_TMUC>1006</MA_TMUC><TEN_TMUC>Thuế thu nhập từ chuyển nhượng bất động sản</TEN_TMUC><LOAI_TK_NSNN>7111</LOAI_TK_NSNN><MA_CQUAN_THU>1054367</MA_CQUAN_THU><SO_CON_PHAI_NOP>4634000</SO_CON_PHAI_NOP><HAN_NOP>2021-08-18 00:00:00.0</HAN_NOP><SHKB>1769</SHKB></ROW_SOTHUE></TONG_TIEN><TONG_TIEN><MA_SO_THUE>8546320781</MA_SO_THUE><TT_NNT>1158500</TT_NNT><ROW_SOTHUE><MA_HS>000.00.32.H19-210708-0032</MA_HS><MA_GCN/><SO_THUADAT>35</SO_THUADAT><SO_TO_BANDO>45</SO_TO_BANDO><DIA_CHI_TS>xã Đại Phước, huyện Nhơn Trạch, tỉnh Đồng Nai</DIA_CHI_TS><MA_TINH_TS>713</MA_TINH_TS><TEN_TINH_TS>Đồng Nai</TEN_TINH_TS><MA_HUYEN_TS>71317</MA_HUYEN_TS><TEN_HUYEN_TS>Huyện Nhơn Trạch</TEN_HUYEN_TS><MA_XA_TS>7131703</MA_XA_TS><TEN_XA_TS>Xã Đại Phước</TEN_XA_TS><MA_DBHC>26476</MA_DBHC><MA_SO_THUE>8546320781</MA_SO_THUE><TEN_NNT>Ông Bùi Hồng Quân, năm sinh 1992 và bà Vũ Thị Lâm Thao, năm sinh 1994</TEN_NNT><SO>151898375DON</SO><MOTA_DIACHI>Nam Thịnh</MOTA_DIACHI><MA_TINH>115</MA_TINH><TEN_TINH>Thái Bình</TEN_TINH><MA_HUYEN>11515</MA_HUYEN><TEN_HUYEN>Huyện Tiền Hải</TEN_HUYEN><MA_XA>1151535</MA_XA><TEN_XA>Xã Nam Thịnh</TEN_XA><SO_QUYET_DINH>LTB2171317-TK0029951/TB-CCT</SO_QUYET_DINH><NGAY_QUYET_DINH>2021-07-20 08:05:25.0</NGAY_QUYET_DINH><MA_CHUONG>757</MA_CHUONG><MA_TMUC>2801</MA_TMUC><TEN_TMUC>Lệ phí trước bạ nhà đất</TEN_TMUC><LOAI_TK_NSNN>7111</LOAI_TK_NSNN><MA_CQUAN_THU>1054367</MA_CQUAN_THU><SO_CON_PHAI_NOP>1158500</SO_CON_PHAI_NOP><HAN_NOP>2021-08-18 00:00:00.0</HAN_NOP><SHKB>1769</SHKB></ROW_SOTHUE></TONG_TIEN></SOTHUE></ROW></BODY></DATA>";
        String json = logicUtils.convertXmlToJson(xml).toString();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonObject lower = logicUtils.jsonKeysLower(jsonObject);
        System.out.println(lower.toString());
    }

    @Test
    public void testParse() throws Exception {
        JSONObject root = new JSONObject("{\"header\":{\"receiver_code\":\"DVCQG\",\"tran_code\":\"05011\",\"spare3\":\"\",\"sender_name\":\"Hệ Thống GIP\",\"sender_code\":\"GIP\",\"version\":\"1\",\"msg_refid\":\"\",\"spare2\":\"\",\"spare1\":\"\",\"original_code\":\"GIP\",\"send_date\":\"03-09-2022\",\"original_date\":\"03-09-2022\",\"error_desc\":\"\",\"original_name\":\"Hệ Thống GIP\",\"receiver_name\":\"Dịch vụ công Quốc gia\",\"error_code\":\"\",\"msg_id\":\"GIP202203093336757\",\"id_link\":\"\"},\"body\":{\"row\":{\"name\":\"Trả lời truy vấn sổ thuế nhà đất\",\"type\":\"00110\",\"sothue\":{\"trangthai\":\"01\",\"mota_trangthai\":\"Tồn tại mã hồ sơ và số CMND/CCCD và có còn nợ thuế đất\",\"tong_tien\":[{\"tt_nnt\":\"32000000\",\"ma_so_thue\":\"5000100042\",\"row_sothue\":{\"ten_huyen\":\"Thành phố Tuyên Quang\",\"dia_chi_ts\":\"352 Giải Phóng\",\"ma_tinh\":\"211\",\"ma_chuong\":\"757\",\"mota_diachi\":\"Thôn 5\",\"so_quyet_dinh\":\"LTB2200000-TK0000010/TB-CCT\",\"loai_tk_nsnn\":\"7111\",\"ten_tinh\":\"Tuyên Quang\",\"ma_so_thue\":\"5000100042\",\"so_thuadat\":\"Thửa\",\"ten_xa_ts\":\"Phường Phúc Xá\",\"shkb\":\"0012\",\"ma_xa\":\"2110121\",\"so\":\"070514776\",\"ma_huyen_ts\":\"10101\",\"so_to_bando\":\"Bản đồ\",\"so_con_phai_nop\":\"32000000\",\"ma_huyen\":\"21101\",\"ma_gcn\":\"\",\"ma_tinh_ts\":\"101\",\"ngay_quyet_dinh\":\"2022-02-17 10:33:28.0\",\"ma_cquan_thu\":\"1054634\",\"ten_tmuc\":\"Lệ phí trước bạ nhà đất\",\"ma_xa_ts\":\"1010101\",\"han_nop\":\"2022-03-18 00:00:00.0\",\"ma_tmuc\":\"2801\",\"ma_dbhc\":\"00001\",\"ten_nnt\":\"Triệu Thị Khánh\",\"ten_huyen_ts\":\"Quận Ba Đình\",\"ma_hs\":\"000.A0.01.H26-210212-9090\",\"ten_tinh_ts\":\"Hà Nội\",\"ten_xa\":\"Xã Lưỡng Vượng\"}},{\"tt_nnt\":\"5052600000\",\"ma_so_thue\":\"5300100124\",\"row_sothue\":[{\"ten_huyen\":\"\",\"dia_chi_ts\":\"đội cấn\",\"ma_tinh\":\"\",\"ma_chuong\":\"757\",\"mota_diachi\":\"\",\"so_quyet_dinh\":\"LTB2200000-TK0000012/TB-CCT\",\"loai_tk_nsnn\":\"7111\",\"ten_tinh\":\"\",\"ma_so_thue\":\"5300100124\",\"so_thuadat\":\"1\",\"ten_xa_ts\":\"Phường Cống Vị\",\"shkb\":\"0012\",\"ma_xa\":\"\",\"so\":\"063018044\",\"ma_huyen_ts\":\"10101\",\"so_to_bando\":\"2\",\"so_con_phai_nop\":\"2520000000\",\"ma_huyen\":\"\",\"ma_gcn\":\"\",\"ma_tinh_ts\":\"101\",\"ngay_quyet_dinh\":\"2022-02-22 09:50:32.0\",\"ma_cquan_thu\":\"1054634\",\"ten_tmuc\":\"Đất được nhà nước giao\",\"ma_xa_ts\":\"1010107\",\"han_nop\":\"2022-05-23 00:00:00.0\",\"ma_tmuc\":\"1401\",\"ma_dbhc\":\"00007\",\"ten_nnt\":\"Lương Thị Thuận\",\"ten_huyen_ts\":\"Quận Ba Đình\",\"ma_hs\":\"000.A0.01.H26-210212-9090\",\"ten_tinh_ts\":\"Hà Nội\",\"ten_xa\":\"\"},{\"ten_huyen\":\"\",\"dia_chi_ts\":\"đội cấn\",\"ma_tinh\":\"\",\"ma_chuong\":\"757\",\"mota_diachi\":\"\",\"so_quyet_dinh\":\"LTB2200000-TK0000012/TB-CCT\",\"loai_tk_nsnn\":\"7111\",\"ten_tinh\":\"\",\"ma_so_thue\":\"5300100124\",\"so_thuadat\":\"1\",\"ten_xa_ts\":\"Phường Cống Vị\",\"shkb\":\"0012\",\"ma_xa\":\"\",\"so\":\"063018044\",\"ma_huyen_ts\":\"10101\",\"so_to_bando\":\"2\",\"so_con_phai_nop\":\"2520000000\",\"ma_huyen\":\"\",\"ma_gcn\":\"\",\"ma_tinh_ts\":\"101\",\"ngay_quyet_dinh\":\"2022-02-22 09:50:32.0\",\"ma_cquan_thu\":\"1054634\",\"ten_tmuc\":\"Đất được nhà nước giao\",\"ma_xa_ts\":\"1010107\",\"han_nop\":\"2022-03-23 00:00:00.0\",\"ma_tmuc\":\"1401\",\"ma_dbhc\":\"00007\",\"ten_nnt\":\"Lương Thị Thuận\",\"ten_huyen_ts\":\"Quận Ba Đình\",\"ma_hs\":\"000.A0.01.H26-210212-9090\",\"ten_tinh_ts\":\"Hà Nội\",\"ten_xa\":\"\"},{\"ten_huyen\":\"\",\"dia_chi_ts\":\"đội cấn\",\"ma_tinh\":\"\",\"ma_chuong\":\"757\",\"mota_diachi\":\"\",\"so_quyet_dinh\":\"LTB2200000-TK0000016/TB-CCT\",\"loai_tk_nsnn\":\"7111\",\"ten_tinh\":\"\",\"ma_so_thue\":\"5300100124\",\"so_thuadat\":\"1\",\"ten_xa_ts\":\"Phường Cống Vị\",\"shkb\":\"0012\",\"ma_xa\":\"\",\"so\":\"063018044\",\"ma_huyen_ts\":\"10101\",\"so_to_bando\":\"2\",\"so_con_phai_nop\":\"12600000\",\"ma_huyen\":\"\",\"ma_gcn\":\"\",\"ma_tinh_ts\":\"101\",\"ngay_quyet_dinh\":\"2022-02-22 09:50:32.0\",\"ma_cquan_thu\":\"1054634\",\"ten_tmuc\":\"Lệ phí trước bạ nhà đất\",\"ma_xa_ts\":\"1010107\",\"han_nop\":\"2022-03-23 00:00:00.0\",\"ma_tmuc\":\"2801\",\"ma_dbhc\":\"00007\",\"ten_nnt\":\"Lương Thị Thuận\",\"ten_huyen_ts\":\"Quận Ba Đình\",\"ma_hs\":\"000.A0.01.H26-210212-9090\",\"ten_tinh_ts\":\"Hà Nội\",\"ten_xa\":\"\"}]},{\"tt_nnt\":\"1000000000\",\"ma_so_thue\":\"4000100192\",\"row_sothue\":{\"ten_huyen\":\"Quận Hải Châu\",\"dia_chi_ts\":\"352 Giải Phóng\",\"ma_tinh\":\"501\",\"ma_chuong\":\"757\",\"mota_diachi\":\"Số 41/7, đường Phan Văn Định\",\"so_quyet_dinh\":\"LTB2200000-TK0000011/TB-CCT\",\"loai_tk_nsnn\":\"7111\",\"ten_tinh\":\"Đà Nẵng\",\"ma_so_thue\":\"4000100192\",\"so_thuadat\":\"Thửa\",\"ten_xa_ts\":\"Phường Phúc Xá\",\"shkb\":\"0012\",\"ma_xa\":\"5010100\",\"so\":\"200049301\",\"ma_huyen_ts\":\"10101\",\"so_to_bando\":\"Bản đồ\",\"so_con_phai_nop\":\"1000000000\",\"ma_huyen\":\"50101\",\"ma_gcn\":\"\",\"ma_tinh_ts\":\"101\",\"ngay_quyet_dinh\":\"2022-02-17 10:33:28.0\",\"ma_cquan_thu\":\"1054634\",\"ten_tmuc\":\"Thuế thu nhập từ chuyển nhượng bất động sản\",\"ma_xa_ts\":\"1010101\",\"han_nop\":\"2022-03-18 00:00:00.0\",\"ma_tmuc\":\"1006\",\"ma_dbhc\":\"00001\",\"ten_nnt\":\"Mai Thị Mỹ Ngọc\",\"ten_huyen_ts\":\"Quận Ba Đình\",\"ma_hs\":\"000.A0.01.H26-210212-9090\",\"ten_tinh_ts\":\"Hà Nội\",\"ten_xa\":\"\"}}]}}}}");
        logicUtils.convertJsonObjectToArray(root.getJSONObject("body").getJSONObject("row"));
        System.out.println(root.toString());
    }


}
