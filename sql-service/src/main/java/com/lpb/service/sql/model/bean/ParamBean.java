package com.lpb.service.sql.model.bean;

import com.lpb.service.sql.utils.constants.EnumInOutField;
import com.lpb.service.sql.utils.constants.EnumTypeField;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParamBean {
    private int idx;
    private EnumTypeField typeField;
    private String valField;
    private EnumInOutField inOutField;

//	public ParamBean(int idx, EnumTypeField typeField, String valField) {
//		this.idx = idx;
//		this.typeField = typeField;
//		this.valField = valField;
//	}
//	public ParamBean(int idx, EnumTypeField typeField, String valField,
//			EnumInOutField inOutField) {
//		this.idx = idx;
//		this.typeField = typeField;
//		this.valField = valField;
//		this.inOutField = inOutField;
//	}
}
