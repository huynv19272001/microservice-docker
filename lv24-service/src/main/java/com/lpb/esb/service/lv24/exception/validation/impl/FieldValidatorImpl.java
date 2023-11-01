package com.lpb.esb.service.lv24.exception.validation.impl;


import com.lpb.esb.service.lv24.exception.FieldValidationException;
import com.lpb.esb.service.lv24.exception.validation.FieldValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.ArrayList;
import java.util.Set;

@Log4j2
@Component
public class FieldValidatorImpl implements FieldValidator {
    ValidatorFactory valdiatorFactory = null;

    public FieldValidatorImpl() {
        valdiatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @Override
    public <T> void validate(T object) throws FieldValidationException {
        Validator validator = valdiatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            ArrayList<String> violationList = new ArrayList<>();
            for (ConstraintViolation<T> violation : violations) {
                String field = "";
                for (Path.Node node : violation.getPropertyPath()) {
                    field = node.getName();
                }
                violationList.add(field + " " + violation.getMessage());
            }
            throw new FieldValidationException("Field Validation Exception", violationList);
        }
    }
}
