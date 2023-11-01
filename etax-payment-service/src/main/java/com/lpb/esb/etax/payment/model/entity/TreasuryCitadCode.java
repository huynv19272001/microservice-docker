package com.lpb.esb.etax.payment.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ESB_TREASURY_CITAD")
public class TreasuryCitadCode {

	@Id
	@Column(name = "TREASURY_ID", nullable = false)
	private Integer treasuryId;

	@Column(name = "TREASURY_CODE", nullable = false)
	private String treasuryCode;

	@Column(name = "TREASURY_NAME")
	private String treasuryName;

	@Column(name = "DIRECT_CODE", nullable = false)
	private String directCode;

	@Column(name = "DIRECT_NAME")
	private String directName;

	@Column(name = "PROVINCE")
	private String province;

}
