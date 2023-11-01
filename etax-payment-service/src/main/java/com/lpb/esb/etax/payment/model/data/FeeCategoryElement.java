/**
 * @author Trung.Nguyen
 * @date 12-Jan-2022
 * */
package com.lpb.esb.etax.payment.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeCategoryElement {

    @JsonProperty("chuong")
    private String categoryCode;

    @JsonProperty("tieuMuc")
    private String subCategoryCode;

    @JsonProperty("diaBanHanhChinh")
    private String locationCode;

}
