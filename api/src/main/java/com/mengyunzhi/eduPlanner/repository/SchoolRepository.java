package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.School;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SchoolRepository extends CrudRepository<School, Long> {
    boolean existsByName(String name);

    @Override
    List<School> findAll();

    List<School> findByNameContaining(String name);
}
