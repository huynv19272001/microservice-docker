package com.lpb.esb.service.query.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class TransactionInfoDTO {
    private String tran_dt;
    private String tran_code;
    private String tran_desc;
    private String merchant_id;
    private String branch;
    private String customer_no;
    private String channel;
    private String tran_ref_no;
}
