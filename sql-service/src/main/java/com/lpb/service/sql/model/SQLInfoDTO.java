package com.lpb.service.sql.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SQLInfoDTO {
    private String sqlCommand;
    private List<SQLParamDTO> listParamSQL;
//	@Override
//	public String toString() {
//		return "SQLInfoDTO [sqlCommand=" + sqlCommand + ", listParmanSQL="
//				+ listParamSQL + "]";
//	}
}
