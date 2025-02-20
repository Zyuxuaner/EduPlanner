package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.entity.Term;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TermRepository extends CrudRepository<Term, Long> {
    Optional<Term> findBySchoolIdAndStatus(Long schoolId, Long status);

    List<Term> findBySchoolId(Long schoolId);

    List<Term> findAllByStatus(Long status);

    @Override
    List<Term> findAll();

    List<Term> findAll(Specification<Term> spec);

}
