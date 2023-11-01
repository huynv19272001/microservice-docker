package com.esb.transaction.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalServiceREQDTO {
    private FCubsHeaderDTO fCubsHeader;
    private FCubsBodyDTO fCubsBody;
}
