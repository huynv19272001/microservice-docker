package com.lpb.esb.settle.utils;

import com.lpb.esb.settle.service.GetTranIDService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class GetTranId {
    @Autowired
    GetTranIDService getTranIDService;

    public String getTranId() {

        String TranID;
        try {
            TranID = getTranIDService.getTranId();
        } catch (Exception e) {
            log.error("Exception getTranID" + e);
            TranID = null;
        }
        return TranID;
    }
}
