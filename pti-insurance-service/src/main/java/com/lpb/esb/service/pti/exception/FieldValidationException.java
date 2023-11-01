package com.lpb.esb.service.pti.exception;

import java.util.List;

public class FieldValidationException extends RuntimeException {
    private List<String> errorList;

    public FieldValidationException(String message, List<String> errorList) {
        super(message);
        this.errorList = errorList;
    }

    public List<String> getErrorList() {
        return errorList;
    }
}
