package com.lpb.esb.service.auth.model.oracle.id;

import lombok.*;

import java.io.Serializable;

/**
 * Created by tudv1 on 2021-07-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbScanClientID implements Serializable {
    private String scanIp;
    private String udf1;
}
