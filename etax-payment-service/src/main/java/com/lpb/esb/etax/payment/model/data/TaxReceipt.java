/**
 * @author Trung.Nguyen
 * @date 28-Apr-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxReceipt {

	@JsonProperty("maDichVu")
	private String serviceCode;
	
	@JsonProperty("maHoSo")
	private String taxReceiptCode;
	
	@JsonProperty("tkthuHuong")
	private String benefitAccountNumber;
	
	@JsonProperty("maLoaiHinhThuPhat")
	private String channelCode;
	
	@JsonProperty("maLoaiHinhThue")
	private String taxCategoryCode;
	
	@JsonProperty("tenLoaiHinhThue")
	private String taxCategoryName;
	
	@JsonProperty("mst")
	private String taxCode;
	
	@JsonProperty("hoTenNguoiNop")
	private String taxPayerName;
	
	@JsonProperty("soCMNDNguoiNop")
	private String taxPayerIdentification;
	
	@JsonProperty("diaChiNguoiNop")
	private String taxPayerAddress;
	
	@JsonProperty("huyenNguoiNop")
	private String taxPayerDistrict;
	
	@JsonProperty("tinhNguoiNop")
	private String taxPayerProvince;
	
	/**
	 * @author: Trung.Nguyen
	 * @since: 23-Aug-2022
	 * @description: add fields taxCode2, taxPayerName2, taxPayerAddress2, taxPayerDistrict2, taxPayerProvince2 (v1.14)
	 * */
	@JsonProperty("mstNopThay")
	private String taxCode2;
	
	@JsonProperty("hoTenNguoiNopThay")
	private String taxPayerName2;
	
	@JsonProperty("diaChiNguoiNopThay")
	private String taxPayerAddress2;
	
	@JsonProperty("huyenNguoiNopThay")
	private String taxPayerDistrict2;
	
	@JsonProperty("tinhNguoiNopThay")
	private String taxPayerProvince2;
	
	@JsonProperty("maCoQuanQD")
	private String taxInstitutionCode;
	
	@JsonProperty("tenCoQuanQD")
	private String taxInstitutionName;
	
	@JsonProperty("khoBac")
	private String localTreasuryCode;
	
	@JsonProperty("ngayQD")
	private String taxDate;
	
	@JsonProperty("soQD")
	private String taxNumber;
	
	@JsonProperty("phiLePhi")
	public List<TaxFee> taxFees;
	
	@JsonProperty("dskhoanNop")
	public List<TaxReceiptDetail> taxReceiptDetails;
	
}