package com.lpb.esb.service.common.model.request.settle;

import com.lpb.esb.service.common.model.request.transaction.SettleBillingDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor

public class ListSettleBillingDTO {
    private List<SettleBillingDTO> listSettleBillingDTO;}
