package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryDTO {
    private String accountNumber;
    private String accountBranch;
    private String accountCCY;
    private String accountType;
    private String drcrIndicator;
    private String lcyAmount;
}
