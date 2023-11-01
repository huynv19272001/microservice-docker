package com.lpb.napas.ecom.service.impl;

import com.lpb.napas.ecom.repository.IEsbUtilDAO;
import com.lpb.napas.ecom.service.IEsbUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsbUtilServiceImpl implements IEsbUtilService {
    @Autowired
    IEsbUtilDAO esbUtilDAO;

    @Override
    public String loadSequencesTypeMsg(String typeMsg) {
        return esbUtilDAO.loadSequencesTypeMsg(typeMsg);
    }
}
