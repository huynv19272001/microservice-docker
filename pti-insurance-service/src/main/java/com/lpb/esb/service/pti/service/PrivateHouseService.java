package com.lpb.esb.service.pti.service;

import com.lpb.esb.service.common.model.response.ResponseModel;
import com.lpb.esb.service.pti.model.lpb.EsbRequestDTO;

public interface PrivateHouseService extends BaseService {
    ResponseModel hoiSoTienBaoHiem(EsbRequestDTO data);

    ResponseModel tinhThoiHanVay(EsbRequestDTO data);

    ResponseModel layMucKhauTru(EsbRequestDTO data);

    ResponseModel layDanhSachDoiTuongBaoHiem(EsbRequestDTO data);

    ResponseModel layDanhSachChuongTrinhBaoHiem(EsbRequestDTO data);

    ResponseModel layDanhSachLoaiHinhNghiepVuBaoHiem(EsbRequestDTO data);

    ResponseModel layDanhSachQuyTacBaoHiem(EsbRequestDTO data);

    ResponseModel layDanhSachTinhThanh(EsbRequestDTO data);

    ResponseModel layDanhSachQuanHuyen(EsbRequestDTO data);

    ResponseModel tinhPhi(EsbRequestDTO data);

    ResponseModel layTyLePhiMacDinhVaNamSuDung(EsbRequestDTO data);

    ResponseModel nhapDon(EsbRequestDTO data);

}
