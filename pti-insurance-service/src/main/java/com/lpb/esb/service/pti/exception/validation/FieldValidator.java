package com.lpb.esb.service.pti.exception.validation;

public interface FieldValidator {
    /**
     * Validate all annotated fields of a DTO object and collect all the validation and
     * then throw them all at once.
     *
     * @param object
     */
    public <T> void validateFields(T object);
}
