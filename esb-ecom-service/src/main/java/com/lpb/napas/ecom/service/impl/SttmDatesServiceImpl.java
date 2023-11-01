package com.lpb.napas.ecom.service.impl;

import com.lpb.napas.ecom.model.SttmDates;
import com.lpb.napas.ecom.repository.ISttmDatesRepository;
import com.lpb.napas.ecom.service.ISttmDatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SttmDatesServiceImpl implements ISttmDatesService {
    @Autowired
    ISttmDatesRepository sttmDatesRepository;

    @Override
    public SttmDates getSttmDatesByBranchCode(String branchCode) {
        return sttmDatesRepository.getSttmDatesByBranchCode(branchCode);
    }
}
