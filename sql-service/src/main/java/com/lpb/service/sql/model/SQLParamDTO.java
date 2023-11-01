package com.lpb.service.sql.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SQLParamDTO {
    private int idx;
    private String inOut;
    private String valType;
    private String sqlClause;
    private String colName;
    private String udf1;
    private String udf2;
    private String udf3;
    private String udf4;

//    @Override
//    public String toString() {
//        return "SQLParamDTO [idx=" + idx + ", inOut=" + inOut + ", valType="
//            + valType + ", sqlClause=" + sqlClause + ", colName=" + colName
//            + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3
//            + ", udf4=" + udf4 + "]";
//    }
}
