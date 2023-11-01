package com.lpb.esb.service.config.model.entities.id;

import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by tudv1 on 2021-11-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class EsbUserPermissionID implements Serializable {
    private String roleId;
    private String username;
}
