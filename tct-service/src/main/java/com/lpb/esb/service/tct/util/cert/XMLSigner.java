package com.lpb.esb.service.tct.util.cert;


import com.google.common.io.CharStreams;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.Charsets;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.*;


/**
 * Created by tudv1 on 2022-03-01
 */
@Log4j2
public class XMLSigner {
    public static void main(String[] args) {
        String data = "<DATA>"
            + "<HEADER><VERSION>1.0</VERSION><SENDER_CODE>01357005</SENDER_CODE><SENDER_NAME>Ngân hàng TMCP Bưu điện Liên Việt</SENDER_NAME><RECEIVER_CODE>GIP</RECEIVER_CODE><RECEIVER_NAME>Hệ Thống GIP</RECEIVER_NAME><TRAN_CODE>05010</TRAN_CODE><MSG_ID>20190430-N112372EB</MSG_ID><MSG_REFID>null</MSG_REFID><ID_LINK/><SEND_DATE>30-Apr-2019 20:48:11</SEND_DATE><ORIGINAL_CODE>01357005</ORIGINAL_CODE><ORIGINAL_NAME>Ngân hàng TMCP Bưu điện Liên Việt</ORIGINAL_NAME><ORIGINAL_DATE>30-Apr-2019 20:48:11</ORIGINAL_DATE><ERROR_CODE/><ERROR_DESC/><SPARE1/><SPARE2/><SPARE3/></HEADER><BODY><ROW><TYPE>00019</TYPE><NAME>Trao đổi danh mục Tiểu Mục</NAME></ROW></BODY>"
            + "</DATA>";
        System.out.println(XMLSigner.sign(data, "tct-service/data-xml-base/template-request.xml", "tct-service/tct-cert/LPB/lpbtct.pfx"));
    }

    public static String sign(String data, String fileTemplate, String pfxFile) {
        try {
            data = data.replaceAll(">\\s*\n\\s*<", "><");
            System.setProperty("javax.xml.transform.TransformerFactory", "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");

            SHASignature shaSignature = new SHASignature();
            KeyStore.PrivateKeyEntry keyEntry = shaSignature.getPrvFromPFX(pfxFile);
            X509Certificate cert = (X509Certificate) keyEntry.getCertificate();

            // Create a DOM XMLSignatureFactory that will be used to
            // generate the enveloped signature.
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

            List<Transform> transforms = new ArrayList<>();
            Map<String, String> namespaces = new HashMap<>(1);
            XPathFilterParameterSpec paramsXpath = new XPathFilterParameterSpec("ancestor-or-self::HEADER or ancestor-or-self::BODY", namespaces);
            transforms.add(fac.newTransform(Transform.XPATH, paramsXpath));


            //http://www.w3.org/2001/04/xmlenc#sha256
            //http://www.w3.org/2000/09/xmldsig#rsa-sha1
            Reference ref = fac.newReference
                ("", fac.newDigestMethod(DigestMethod.SHA1, null)
                    , transforms
                    , null
                    , null
                );

            // Create the SignedInfo.
            // http://www.w3.org/2001/04/xmldsig-more#rsa-sha256
            // http://www.w3.org/2000/09/xmldsig#sha1
            SignedInfo si = fac.newSignedInfo
                (fac.newCanonicalizationMethod
                        (CanonicalizationMethod.EXCLUSIVE_WITH_COMMENTS,
                            (C14NMethodParameterSpec) null),
                    fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                    Collections.singletonList(ref));


            // Create the KeyInfo containing the X509Data.
            KeyInfoFactory kif = fac.getKeyInfoFactory();
            List x509Content = new ArrayList();
            x509Content.add(cert.getSubjectX500Principal().getName());
            x509Content.add(cert);

            X509Data xd = kif.newX509Data(x509Content);
            KeyValue keyValue = kif.newKeyValue(cert.getPublicKey());
            KeyInfo ki = kif.newKeyInfo(Arrays.asList(keyValue, xd));
//            KeyInfo ki = kif.newKeyInfo(Collections.emptyList());

            javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();

            dbf.setNamespaceAware(true);
            dbf.setIgnoringElementContentWhitespace(true);
            dbf.setValidating(false);

            DocumentBuilder db = dbf.newDocumentBuilder();

            // Disable loading of external Entityes
            // db.setEntityResolver(null);
            db.setEntityResolver(new EntityResolver() {
                // Dummi resolver - always do nothing
                public InputSource resolveEntity(String publicId, String systemId) {
                    return new InputSource(new StringReader(""));
                }
            });

            InputStream inputStream = new ByteArrayInputStream(data.getBytes());
            Document doc = dbf.newDocumentBuilder().parse(inputStream);

            // Document doc = dbf.newDocumentBuilder().parse(new FileInputStream("tct-service/data-xml-base/purchaseOrder.xml"));
            Element security = doc.createElement("SECURITY");
            doc.getElementsByTagName("DATA").item(0).appendChild(security);

            // Create a DOMSignContext and specify the RSA PrivateKey and
            // location of the resulting XMLSignature's parent element.
            DOMSignContext dsc = new DOMSignContext(keyEntry.getPrivateKey(), doc.getDocumentElement().getLastChild());

            // Create the XMLSignature, but don't sign it yet.
            XMLSignature signature = fac.newXMLSignature(si, ki);

            // Marshal, generate, and sign the enveloped signature.
            signature.sign(dsc);
//            Element element = (Element) doc.getElementsByTagName("KeyInfo").item(0);
//
//            // remove the specific node
//            element.getParentNode().removeChild(element);

            String res = getStringFromDocument(doc).replaceAll("&#13;", "");
            return generateFullMsg(fileTemplate, res);
        } catch (Exception e) {
            log.error("error when sign: {}", e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    public static String generateFullMsg(String fileTemplate, String xml) throws IOException {
        String template = CharStreams.toString(new InputStreamReader(
            new FileInputStream(fileTemplate), Charsets.UTF_8));
        return String.format(template, xml);
    }

    public static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
