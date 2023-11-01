//package com.lpb.esb.service.tct.util.cert;
//
//import com.sun.org.apache.xml.internal.serialize.OutputFormat;
//import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
//
//import org.apache.xml.security.keys.content.X509Data;
//import org.apache.xml.security.signature.XMLSignature;
//import org.apache.xml.security.transforms.Transforms;
//import org.apache.xml.security.transforms.params.XPathContainer;
//import org.apache.xml.security.utils.Constants;
//import org.apache.xml.security.utils.ElementProxy;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.xml.sax.EntityResolver;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
////import weblogic.apache.xml.serialize.OutputFormat;
////import weblogic.apache.xml.serialize.XMLSerializer;
//
//import javax.xml.parsers.DocumentBuilder;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.security.PrivateKey;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//
///**
// * Created by tudv1 on 2022-02-25
// */
//public class CertSign {
//    static {
//        org.apache.xml.security.Init.init();
//    }
//
//    final static String PFX_FILE = "tct-service/tct-cert/SHA256/lpb-tcthue.pfx";
//    final static String CERT_FILE = "tct-service/tct-cert/SHA256/lpb-tcthue.cer";
//
//    public static void main(String[] args) throws Exception {
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
//        String sign = sign(""
//                + "<DATA_REQUEST>"
//                + "<HEADER><VERSION>1.0</VERSION><SENDER_CODE>01357005</SENDER_CODE><SENDER_NAME>Ngân hàng TMCP Bưu điện Liên Việt</SENDER_NAME><RECEIVER_CODE>GIP</RECEIVER_CODE><RECEIVER_NAME>Hệ Thống GIP</RECEIVER_NAME><TRAN_CODE>00046</TRAN_CODE><MSG_ID>01310001000000034096</MSG_ID><MSG_REFID/><ID_LINK/><SEND_DATE>26-Feb-2022 10:07:46</SEND_DATE><ORIGINAL_CODE>GIP</ORIGINAL_CODE><ORIGINAL_NAME>Hệ Thống GIP</ORIGINAL_NAME><ORIGINAL_DATE/><ERROR_CODE/><ERROR_DESC/></HEADER><BODY><ROW><TYPE>00046</TYPE><NAME>Truy vấn thu nộp theo MST</NAME><TRUYVAN><MST>0108937704</MST><SO_TKHAI/></TRUYVAN></ROW></BODY>"
//                + "</DATA_REQUEST>"
//            ,
//            PFX_FILE, CERT_FILE);
//        System.out.println("\n\n\n" + sign);
//    }
//
//    public static String sign(String data, String pfxFile, String certFile) throws Exception {
//        try {
//
//            ElementProxy.setDefaultPrefix(Constants.SignatureSpecNS, "");
//
//            // TUV
//            SHASignature shaSignature = new SHASignature();
//            PrivateKey privateKey = shaSignature
//                .getPrivateKeyFromPFX(pfxFile);
//
//            javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
//
//            dbf.setNamespaceAware(true);
//            dbf.setIgnoringElementContentWhitespace(true);
//            dbf.setValidating(false);
//
//            DocumentBuilder db = dbf.newDocumentBuilder();
//
//            // Disable loading of external Entityes
//            // db.setEntityResolver(null);
//            db.setEntityResolver(new EntityResolver() {
//                // Dummi resolver - always do nothing
//
//                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
//                    return new InputSource(new StringReader(""));
//                }
//            });
//
//            org.w3c.dom.Document doc = db.newDocument();
//            Element root = doc.createElement("DATA");
//            doc.appendChild(root);
////            Document valueDoc = db.parse(new InputSource(new StringReader(data)));
////            Node valueElement = doc.importNode(valueDoc.getDocumentElement(), true);
////            root.appendChild(valueElement);
////
////            Element security = doc.createElement("SECURITY");
////            root.appendChild(security);
//
//            Document dataRequest = db.parse(new InputSource(new StringReader(data)));
//            Node headerElement = doc.importNode(dataRequest.getDocumentElement().getFirstChild(), true);
//            root.appendChild(headerElement);
//            Node bodyElement = doc.importNode(dataRequest.getDocumentElement().getLastChild(), true);
//            root.appendChild(bodyElement);
//            Element security = doc.createElement("SECURITY");
//            root.appendChild(security);
//
//
//            XMLSignature sig = new XMLSignature(doc, "", XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256, "http://www.w3.org/2001/10/xml-exc-c14n#WithComments");
//            security.appendChild(sig.getElement());
//            sig.getSignedInfo().addResourceResolver(new OfflineResolver());
//            {
//                // create the transforms object for the Document/Reference
//                Transforms transforms = new Transforms(doc);
//                XPathContainer xpathC = new XPathContainer(doc);
//                xpathC.setXPath("ancestor-or-self::HEADER or ancestor-or-self::BODY");
//
//                transforms.addTransform(Transforms.TRANSFORM_XPATH, xpathC.getElementPlusReturns());
//                sig.addDocument("", transforms, "http://www.w3.org/2001/04/xmlenc#sha256");
//            }
//
//            // TUV
//            FileInputStream fin = new FileInputStream(certFile);
//            CertificateFactory f = CertificateFactory.getInstance("X.509");
//            X509Certificate certificate = (X509Certificate) f.generateCertificate(fin);
//
//            X509Data x509data = new X509Data(doc);
//            x509data.addSubjectName(certificate);
//            x509data.addCertificate(certificate);
//            sig.getKeyInfo().add(x509data);
//
//            // sig.addKeyInfo(certificate);
//            // sig.addKeyInfo(certificate.getSubjectDN().toString());
//            sig.sign(privateKey);
//
//            // Serialize DOM
//            OutputFormat format = new OutputFormat(doc);
//            format.setLineSeparator("");
//            // as a String
//            StringWriter stringOut = new StringWriter();
//            XMLSerializer serial = new XMLSerializer(stringOut, format);
//            serial.serialize(doc);
//            // Display the XML
//
//            String res = stringOut.toString()
//                .replaceAll("\n", "")
//                .replaceAll("&#xd;", "")
////                .replace("<DATA_REQUEST>", "").replace("</DATA_REQUEST>", "")
//                ;
//
////            return res;
//            return geenFullMsg(res);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw ex;
//        }
//    }
//
//
//    public static String geenFullMsg(String xml) {
//        String baseXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:gip=\"http://gip.request.com/\">\n" +
//            "   <soapenv:Header/>\n" +
//            "   <soapenv:Body>\n" +
//            "      <gip:xuLyTruyVanMsg>\n" +
//            "         <in_msg><![CDATA[%s]]></in_msg>\n" +
//            "      </gip:xuLyTruyVanMsg>\n" +
//            "   </soapenv:Body>\n" +
//            "</soapenv:Envelope>";
//
//        String fullXml = String.format(baseXml, xml);
//        return fullXml;
//    }
//}
