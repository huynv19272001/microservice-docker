package com.lpb.esb.service.cache.model.oracle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lpb.esb.service.cache.model.oracle.id.EsbErrorMessageID;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by tudv1 on 2021-08-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@Entity
@Table(name = "ESB_ERROR_MESSAGE")
@IdClass(EsbErrorMessageID.class)
public class EsbErrorMessageEntity implements Serializable {
    @Id
    @Column(name = "MODULE_CODE")
    private String moduleCode;
    @Id
    @Column(name = "CODE_NUMBER")
    private Integer codeNumber;
    @Basic
    @Column(name = "ERROR_ID")
    private Integer errorId;
    @Basic
    @Column(name = "ERROR_CODE")
    private String errorCode;
    @Basic
    @Column(name = "LANG_ID")
    private String langId;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic
    @Column(name = "SYS_MESSAGE")
    private String sysMessage;
    @Basic
    @Column(name = "CUS_MESSAGE")
    private String cusMessage;
    @Basic
    @Column(name = "MODULE_ID")
    private Integer moduleId;
    @Basic
    @Column(name = "RECORD_STAT")
    private String recordStat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Basic
    @Column(name = "MAKER_DT")
    private Date markerDt;
    @Basic
    @Column(name = "MAKER_ID")
    private String markerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @Basic
    @Column(name = "CHECKER_DT")
    private Date checkerDt;
    @Basic
    @Column(name = "CHECKER_ID")
    private String checkerId;
}
