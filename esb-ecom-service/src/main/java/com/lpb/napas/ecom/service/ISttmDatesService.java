package com.lpb.napas.ecom.service;

import com.lpb.napas.ecom.model.SttmDates;

public interface ISttmDatesService {
    SttmDates getSttmDatesByBranchCode(String branchCode);
}
