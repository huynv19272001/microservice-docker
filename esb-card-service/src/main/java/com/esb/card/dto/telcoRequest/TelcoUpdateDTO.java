package com.esb.card.dto.telcoRequest;

import lombok.*;

/**
 * Created by cuongnm on 2022-07-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class TelcoUpdateDTO {
    String name;
    String value;
}
