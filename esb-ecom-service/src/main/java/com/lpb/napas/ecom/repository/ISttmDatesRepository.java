package com.lpb.napas.ecom.repository;

import com.lpb.napas.ecom.model.SttmDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISttmDatesRepository extends JpaRepository<SttmDates, String> {
    @Query("SELECT new com.lpb.napas.ecom.model.SttmDates(a.today) FROM SttmDates a WHERE a.branchCode = :branchCode")
    SttmDates getSttmDatesByBranchCode(@Param("branchCode") String branchCode);
}
