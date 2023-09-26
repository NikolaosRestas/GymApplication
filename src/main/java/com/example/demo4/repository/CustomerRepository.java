package com.example.demo4.repository;

import com.example.demo4.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Transactional
    @Modifying
    @Query("delete from Customer cust where cust.id = :customerId")
    int deleteCustomerById(@Param("customerId") Long id);
}
