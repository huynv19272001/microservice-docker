package com.lpb.napas.ecom.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadTransferIO {
    private String branchCode;
    private String description;
    private String sourceCode;
    private String txnCode;
    private String valueDt;
    private String makerId;
    private String checkerId;
    private String productReference;
    private String trnRefNo;
    private List<EntryDTO> listEntry;
}
