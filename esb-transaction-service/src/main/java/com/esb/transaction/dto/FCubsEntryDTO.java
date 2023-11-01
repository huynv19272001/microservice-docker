package com.esb.transaction.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FCubsEntryDTO {
    private List<EntryDTO> entry;
}
