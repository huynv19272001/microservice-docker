package com.esb.card.respository;

import com.esb.card.dto.entities.EsbErrorPartnerMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by cuongnm10 on 2022-07-07
 */
@Repository
public interface EsbErrorPartnerMessageRepository extends JpaRepository<EsbErrorPartnerMessageEntity, String> {

    @Query(value = "select CODE_ID,SERVICE_ID,ERROR_CODE,ERROR_DESC,PARTNER_ID,PARTNER_CODE,PARTNER_DESC,DESCRIPTION,MAKER_ID,MAKER_DT from ESB_ERROR_PARTNER_MESSAGE where SERVICE_ID = ?1  and PARTNER_ID = ?2 and PARTNER_CODE = ?3",
        nativeQuery = true)
    EsbErrorPartnerMessageEntity getErrorMessage(String serviceId,String partnerId, String partnerCode);
}
