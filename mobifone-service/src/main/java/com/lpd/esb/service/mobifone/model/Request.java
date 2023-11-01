package com.lpd.esb.service.mobifone.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class Request {
    private Header header;
    private RequestBody body;
}
