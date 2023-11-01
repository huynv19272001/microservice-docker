package com.esb.card.dto.telcoRequest;

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
