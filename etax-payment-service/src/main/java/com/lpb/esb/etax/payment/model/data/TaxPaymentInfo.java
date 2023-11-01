/**
 * @author Trung.Nguyen
 * @date 28-Apr-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxPaymentInfo {
	
	@JsonProperty("maDoiTac")
	private String partnerCode;
	
	@JsonProperty("maThamChieu")
	private String referenceNumber;
	
	@JsonProperty("soTien")
	private String amount;
	
	@JsonProperty("phiChuyenPhat")
	private String deliveryFee;
	
	@JsonProperty("ngonNgu")
	private String language;
	
	@JsonProperty("maTienTe")
	private String currency;
	
	@JsonProperty("thongTinGiaoDich")
	private String narration;
	
	@JsonProperty("thoiGianGD")
	private String transTime;
	
	@JsonProperty("maNganHang")
	private String bankCode;
	
	@JsonProperty("phuongThuc")
	private String paymentMethod;
	
	@JsonProperty("soTaiKhoan_The")
	private String debitAccountNumber;
	
	@JsonProperty("ewalletToken")
	private String ewalletToken;
	
	@JsonProperty("ThongTinBienLai")
	public TaxReceipt taxReceipt;
	
}
