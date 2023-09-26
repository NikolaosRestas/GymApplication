package com.example.demo4.repository;

import com.example.demo4.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    @Transactional
    @Modifying
    @Query("delete from Program cust where cust.id = :programId")
    int deleteProgramById(@Param("programId") Long id);
}
