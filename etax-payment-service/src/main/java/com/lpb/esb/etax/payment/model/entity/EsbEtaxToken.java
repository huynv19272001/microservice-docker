package com.lpb.esb.etax.payment.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ESB_ETAX_TOKEN")
public class EsbEtaxToken {
	
	@Id
	@Column(name = "LINK_REQ_ID")
	private String linkReqId;
	
	@Column(name = "UNLINK_REQ_ID")
	private String unlinkReqId;
	
	@Column(name = "TOKEN_DATA")
	private String tokenData;
	
	@Column(name = "ORIGINAL_TYPE")
	private String originalType;
	
	@Column(name = "ORIGINAL_CLASS")
	private String originalClass;
	
	@Column(name = "ORIGINAL_DATA")
	private String originalData;
	
	@Column(name = "ORIGINAL_SYSTEM")
	private String originalSystem;
	
	@Column(name = "TAX_CODE")
	private String taxCode;
	
	@Column(name = "FULL_NAME")
	private String fullName;
	
	@Column(name = "IDENTIFICATION_TYPE")
	private String identificationType;
	
	@Column(name = "IDENTIFICATION_NUMBER")
	private String identificationNumber;
	
	@Column(name = "CREATED_DATE")
	private String createdDate;			// date
	
	@Column(name = "MODIFIED_DATE")
	private String modifiedDate;		// date
	
	@Column(name = "STATUS")
	private String status;
	
}
