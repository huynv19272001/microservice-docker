package com.lpb.service.sql.model;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SQLCommandInfo {
    @Column(name = "EXECUTED_BY")
    private String executedBy;
    @Column(name = "CONNECTOR_NAME")
    private String connectorName;
    @Column(name = "URL_API")
    private String urlAPI;
    @Column(name = "CONNECTOR_URL")
    private String connectorURL;
    @Column(name = "CONNECTOR_PORT")
    private String connectorPort;
    @Column(name = "METHOD_ACTION")
    private String methodAction;
    @Column(name = "UDF1")
    private String UDF1;
    @Column(name = "UDF2")
    private String UDF2;
    @Column(name = "UDF3")
    private String UDF3;
    @Column(name = "UDF4")
    private String UDF4;
    @Column(name = "UDF5")
    private String UDF5;
    @Column(name = "UDF6")
    private String UDF6;
    @Column(name = "UDF7")
    private String UDF7;
    @Column(name = "UDF8")
    private String UDF8;
    @Column(name = "IDX")
    private String IDX;
    @Column(name = "IN_OUT")
    private String IN_OUT;
    @Column(name = "VALUE_TYPE")
    private String valueType;
    @Column(name = "SQL_CLAUSE")
    private String sqlClause;
    @Column(name = "SQL_COMMAND")
    private String sqlCommand;
    @Column(name = "RECORD_STAT")
    private String recordStat;
}
