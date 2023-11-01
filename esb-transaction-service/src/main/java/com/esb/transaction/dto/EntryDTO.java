package com.esb.transaction.dto;

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

    /**
     * @author Trung.Nguyen
     * @date 15-Sep-2022
     * description: append these fields for transfer by Citad
     * */
    private String senderInstAccount;
    private String receiverName;
    private String receiverAddress;
    private String receiverAccount;
    private String receiverCode;
}
