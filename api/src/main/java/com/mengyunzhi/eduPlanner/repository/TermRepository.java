package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Term;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TermRepository extends CrudRepository<Term, Long> {
    Optional<Term> findBySchoolIdAndStatus(Long schoolId, Long status);

    List<Term> findBySchoolId(Long schoolId);

    List<Term> findAllByStatus(Long status);

    @Override
    List<Term> findAll();

    @Query("SELECT t FROM Term t WHERE t.status = :status AND t.startTime <= :startTime AND t.endTime >= :endTime")
    List<Term> findByStatusAndStartTimeAndEndTime(@Param("status") Long status, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
