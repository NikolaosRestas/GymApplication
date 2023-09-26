package com.example.demo4.repository;

import com.example.demo4.model.Gym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {

    @Transactional
    @Modifying
    @Query("delete from Gym cust where cust.id = :gymId")
    int deleteGymById(@Param("gymId") Long id);

    List<Gym> findAllByCounty_Id(Long countyId);
}