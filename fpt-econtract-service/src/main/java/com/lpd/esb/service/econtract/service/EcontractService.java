package com.lpd.esb.service.econtract.service;

import com.lpd.esb.service.econtract.model.RequestDTO;
import com.lpd.esb.service.econtract.model.ResponseDTO;

public interface EcontractService{
    ResponseDTO econtract(RequestDTO request);
}
