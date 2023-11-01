package com.lpb.esb.service.pti.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;

public interface CreditBaoAnService extends BaseService {
    ResponseModel tinhPhi(EsbRequestDTO data);

    ResponseModel nhapDon(EsbRequestDTO data);

    ResponseModel chiaKyPhi(EsbRequestDTO data);

    ResponseModel hinhThucThamGia(EsbRequestDTO data);

    ResponseModel catKyPhi(EsbRequestDTO data);

    ResponseModel chiTiet(EsbRequestDTO data);

    ResponseModel tinhDuNo(EsbRequestDTO data);
}
