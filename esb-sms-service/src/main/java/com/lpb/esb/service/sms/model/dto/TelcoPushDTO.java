package com.lpb.esb.service.sms.model.dto;

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
