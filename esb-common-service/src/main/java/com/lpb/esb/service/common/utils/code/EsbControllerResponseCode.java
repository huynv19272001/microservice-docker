package com.lpb.esb.service.common.utils.code;

/**
 * Created by tudv1 on 2021-07-19
 */
public enum EsbControllerResponseCode {
    SUCCESS("00"), FAIL("99");

    public final String label;

    private EsbControllerResponseCode(String label) {
        this.label = label;
    }
}
