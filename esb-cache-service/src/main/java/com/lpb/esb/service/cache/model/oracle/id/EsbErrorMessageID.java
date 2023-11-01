package com.lpb.esb.service.cache.model.oracle.id;

import lombok.*;

import java.io.Serializable;

/**
 * Created by tudv1 on 2021-08-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbErrorMessageID implements Serializable {
    private String moduleCode;
    private Integer codeNumber;
}
