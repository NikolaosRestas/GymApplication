package com.example.demo4.repository;

import com.example.demo4.model.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CountyRepository extends JpaRepository<County,Long> {
    @Transactional
    @Modifying
    @Query("delete from County cust where cust.id = :countyId")
    int deleteCountyById(@Param("countyId") Long id);

}
