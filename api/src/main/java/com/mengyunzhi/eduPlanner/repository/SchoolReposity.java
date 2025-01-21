package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.School;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SchoolReposity extends CrudRepository<School, Long> {
    List<School> findAllByNameContains(String name);

    @Override
    List<School> findAll();
}
