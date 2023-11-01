package com.lpb.esb.service.common.model.request.settle;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor

public class RsGetBill {
    private List<RsGetBillDTO> listRsGetBillDTO;
}
