package com.lpb.esb.service.sms.model.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class TelcoBodyDTO {
    private List<TelcoUpdateDTO> params;
}
