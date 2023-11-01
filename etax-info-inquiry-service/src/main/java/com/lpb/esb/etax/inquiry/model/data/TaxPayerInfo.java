/**
 * @author Trung.Nguyen
 * @date 19-Apr-2022
 * */
package com.lpb.esb.etax.inquiry.model.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxPayerInfo {

	@JsonProperty("MaDoiTac")
	private String partnerCode;

	@JsonProperty("Loai")
	private String actionCode;

	@JsonProperty("Mst")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private String taxCode;

	@JsonProperty("Ten_nnt")
	private String payerFullName;

	@JsonProperty("LoaiGiayTo")
	private String identificationType;

	@JsonProperty("SoGiayTo")
	private String identificationNumber;

	@JsonProperty("DienThoai")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private String phoneNumber;

	@JsonProperty("PhuongThuc")
	private String requireType;

	@JsonProperty("SoTaiKhoan_The")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private String requireNumber;

	@JsonProperty("NgayPhatHanh")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MM/yy")
	private String issueDate;

	@JsonProperty("Email")
	private String email;

	@JsonProperty("EwalletToken")
	private String ewalletToken;

}
