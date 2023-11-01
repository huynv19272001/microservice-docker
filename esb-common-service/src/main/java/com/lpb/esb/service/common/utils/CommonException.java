package com.lpb.esb.service.common.utils;

import com.lpb.esb.service.common.model.response.ResponseModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonException extends RuntimeException {
    private ResponseModel responseModel;
}
