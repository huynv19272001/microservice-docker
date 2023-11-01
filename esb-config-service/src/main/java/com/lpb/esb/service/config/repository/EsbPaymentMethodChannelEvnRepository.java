package com.lpb.esb.service.config.repository;

import com.lpb.esb.service.config.model.entities.EsbPaymentMethodChannelEvnEntity;
import com.lpb.esb.service.config.model.entities.id.EsbPaymentMethodChannelEvnId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsbPaymentMethodChannelEvnRepository extends JpaRepository<EsbPaymentMethodChannelEvnEntity, EsbPaymentMethodChannelEvnId> {
    EsbPaymentMethodChannelEvnEntity findByRecordStatAndProductCodeAndPaymentMethodAndChannelAndBillPrint(String recordStat, String productCode, String paymentMethod, String channel, String billPrint);
}
