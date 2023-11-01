package com.lpb.esb.service.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpb.esb.service.common.utils.rsa.RSAUtil;
import com.lpb.esb.service.common.utils.rsa.SHA1Utils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicUtilsTest {

    @Test
    void createPassEsb() throws Exception {
        String orgPass = "12345678";
        String keyRsa = "<RSAKeyValue><Modulus>sGvfa/LtdCJQ36v+n2XMX8Z54WIIC4JUpYuyCCdvE+oVHJ2w8u5nWKZHLnqMOL9qHyWMtN1RKoAzu/+kGqzUAXPAOuNF2kzgMZL5WlXyiymAaGk50L5LZekZydHfXdGumFlo2JNTPQwEFHc3PQQuG/LnTkB2YwaSDBqp/K6Pf/s=</Modulus><Exponent>AQAB</Exponent><P>2//W+D7h61BIiapOZzvMJHwPIj8efuY4s5tIuahe2UGhUqFyQfTlgZgj8jekAfua+W4Iv1eZfchMBQpFEmSqMw==</P><Q>zUp5ZVjAUWdzv9/HUs5VELrelNPjDirezz1ixiBcmBcZNBvxSLgwZ7nvtZLyNjbNC0t1CbhvZGoHl2auP5qbGQ==</Q><DP>HQl1jCpwkyhziogJ9SI1XZNqQ/Dky4cDcOFMBgUtD6vT6R4aAI6cRDCaffvOD9zvpYBaczbSsYFgV0H1v+m9dQ==</DP><DQ>TfqiDiseMk23nASms/+INGinL6UktDb4l4PbxyJ3yZXMjwymJFdCm8P3F+OPeaRrUoUBGgYi2eOkqo/oVGSn0Q==</DQ><InverseQ>ID6AgSwkV2MpKRttlnG38ebtDUlRlkw6DR9tiAmjle4Hz0wMrLqzbqPMtQlhJHBe89IU0+i0INTpIcK5yvFAcw==</InverseQ><D>OdQCNjY403p0G/0ax1Ds/u/l/5TMokkkSedSgg27AUIXjXu7BsZSpeJLLtcIo4lK1c7PInHxKwc+7f7L4dqI2y2YHTmYc9QIDvn1OP4J3sEuXMOMqRmvzQcDjVbHCI6fVsi5XvFo1ScwRce4C6yNYttNtSmOKToHDkiBlan7tOE=</D></RSAKeyValue>";
        JSONObject jsonObject = new JSONObject()
            .put("password", RSAUtil.encryptData(orgPass, keyRsa))
            .put("username", "LCIM_CORE_USER_EB");
        ObjectMapper mapper = new ObjectMapper();
//        System.out.println(mapper
//            .writerWithDefaultPrettyPrinter()
//            .writeValueAsString(mapper
//                .readTree(jsonObject.toString()
//                )
//            )
//        );
        System.out.println(jsonObject.toString(4));
    }
}
