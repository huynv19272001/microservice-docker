package com.lpb.service.bidv.common;

import com.lpb.esb.service.common.model.response.ResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionCT001 extends RuntimeException {
    ResponseModel ResponseModel;
}
