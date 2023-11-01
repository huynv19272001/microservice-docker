package com.esb.card.dto.getaccountlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountListDTO {
    private String accountNumber;
    private String recordPerPage;
    private String pageNumber;
}
