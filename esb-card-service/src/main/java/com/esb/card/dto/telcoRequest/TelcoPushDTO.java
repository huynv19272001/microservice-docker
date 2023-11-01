package com.esb.card.dto.telcoRequest;

import lombok.*;

import java.util.List;

/**
 * Created by cuongnm on 2022-07-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class TelcoPushDTO {
    private TelcoHeaderDTO header;
    private TelcoBodyDTO body;
}
