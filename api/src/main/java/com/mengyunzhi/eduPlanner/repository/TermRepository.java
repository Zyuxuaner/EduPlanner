package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Term;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TermRepository extends CrudRepository<Term, Long> {
    Optional<Term> findBySchool_idAndStatus(Long schoolId, Long status);
    @Override
    List<Term> findAll();
}
