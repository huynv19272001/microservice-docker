package com.esb.transaction.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalServiceRESDTO {
    private FCubsHeaderDTO fCubsHeader;
    private FCubsBodyDTO fCubsBody;
}
