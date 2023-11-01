package com.lpb.esb.service.pti.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;

public interface CarService extends BaseService {
    ResponseModel hieuXe(EsbRequestDTO data);

    ResponseModel loaiXe(EsbRequestDTO data);

    ResponseModel chiTietLoaiXe(EsbRequestDTO data);

    ResponseModel tinhPhi(EsbRequestDTO data);

    ResponseModel chiaKyPhi(EsbRequestDTO data);

    ResponseModel duyetDon(EsbRequestDTO data);

    ResponseModel inDon(EsbRequestDTO esbRequestDTO);

    ResponseModel layMa(EsbRequestDTO esbRequestDTO);

    ResponseModel capDon(EsbRequestDTO esbRequestDTO);
}
