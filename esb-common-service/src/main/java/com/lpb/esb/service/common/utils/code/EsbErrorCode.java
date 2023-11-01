package com.lpb.esb.service.common.utils.code;

/**
 * Created by tudv1 on 2021-07-19
 */
public enum EsbErrorCode {
    SUCCESS("00"), INVALID("11"), TRAN_FAIL("98"), TIME_OUT("99"), FAIL("99"), NO_DATA("90");

    public final String label;

    private EsbErrorCode(String label) {
        this.label = label;
    }
}
