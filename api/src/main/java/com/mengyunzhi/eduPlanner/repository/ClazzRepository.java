package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Clazz;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClazzRepository extends CrudRepository<Clazz,Long> {
    @Override
    List<Clazz> findAll();

    List<Clazz> findBySchoolId(Long schoolId);

}
