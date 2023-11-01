package com.lpb.service.sql.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FcubsErrorDTO{
	private String ECODE;
    private String EDESC;
    private String REFCODE;
    private String REFDESC;
}
