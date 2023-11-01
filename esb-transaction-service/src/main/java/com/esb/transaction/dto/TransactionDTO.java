package com.esb.transaction.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private TransactionInfoDTO transactionInfo;
    private ServiceDTO service;
    private PartnerDTO partnerInfo;
    private List<PostInfoDTO> postInfo;
}
