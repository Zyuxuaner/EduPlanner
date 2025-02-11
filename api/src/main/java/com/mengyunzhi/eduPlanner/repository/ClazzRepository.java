package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Clazz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClazzRepository extends CrudRepository<Clazz,Long> {
    List<Clazz> findClazzBySchoolId(Long schoolId);

    @Override
    List<Clazz> findAll();

    List<Clazz> findBySchoolId(Long schoolId);

    // 根据学校 ID 和班级名称查询班级
    Optional<Clazz> findBySchoolIdAndName(Long schoolId, String name);

    @Query("SELECT c FROM Clazz c WHERE (:schoolId IS NULL OR c.school.id = :schoolId) AND (:clazzName IS NULL OR c.name LIKE %:clazzName%)")
    List<Clazz> searchClazzes(Long schoolId, String clazzName);

}
