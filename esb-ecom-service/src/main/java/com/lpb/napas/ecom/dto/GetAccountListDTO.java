package com.lpb.napas.ecom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetAccountListDTO {
    private String accountNumber;
    private String recordPerPage;
    private String pageNumber;
}
