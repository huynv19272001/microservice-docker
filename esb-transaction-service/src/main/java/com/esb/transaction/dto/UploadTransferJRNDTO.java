package com.esb.transaction.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadTransferJRNDTO {
    private FCubsHeaderDTO fCubsHeader;
    private UploadTransferIO uploadTransferIO;
}
