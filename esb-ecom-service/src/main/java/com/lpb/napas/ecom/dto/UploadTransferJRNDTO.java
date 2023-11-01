package com.lpb.napas.ecom.dto;

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
