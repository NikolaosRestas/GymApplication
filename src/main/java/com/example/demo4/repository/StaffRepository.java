package com.example.demo4.repository;

import com.example.demo4.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Transactional
    @Modifying
    @Query("delete from Staff cust where cust.id = :staffId")
    int deleteStaffById(@Param("staffId") Long id);
}