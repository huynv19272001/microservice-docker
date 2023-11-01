package com.lpb.esb.service.common.utils;

import com.lpb.esb.service.common.model.response.ResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionUtils extends RuntimeException {
    ResponseModel responseModel;
}
