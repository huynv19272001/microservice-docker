package com.lpb.service.sql.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassCore {
	private String PASSWORD;
	private String GENERATE_PASSWORD;
	private String SALT;
}
