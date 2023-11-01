package com.lpb.service.payoo.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractBody {
    private static Logger logger = LogManager.getLogger(AbstractBody.class);

    @Transient
    String[] secretFields;

    @Override
    public String toString() {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.writeValueAsString(this);
        } catch (Throwable ex) {
            logger.error("!!! Error converting response toString", ex);
            List<Field> fields = Arrays.asList(getClass().getDeclaredFields());
            return fields.stream().map(Field::getName).collect(Collectors.joining(", "));
        }
    }

}
