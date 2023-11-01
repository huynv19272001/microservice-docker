package com.lpd.esb.service.econtract.service;

import com.lpd.esb.service.econtract.model.LpbRequestDTO;
import com.lpd.esb.service.econtract.model.ResponseDTO;

public interface LpbEofficeService {
    ResponseDTO sendFile(LpbRequestDTO request);
}
