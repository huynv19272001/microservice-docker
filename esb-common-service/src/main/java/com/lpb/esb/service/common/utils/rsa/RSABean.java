package com.lpb.esb.service.common.utils.rsa;

import java.io.Serializable;

/**
 * Object Data RSA
 *
 * @author admin
 */
public class RSABean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String modulus;
    private String delement;

    public String getModulus() {
        return modulus;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    public String getDelement() {
        return delement;
    }

    public void setDelement(String delement) {
        this.delement = delement;
    }


}
