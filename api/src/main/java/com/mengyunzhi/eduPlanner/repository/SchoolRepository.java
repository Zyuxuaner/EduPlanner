package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.School;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SchoolRepository extends CrudRepository<School, Long> {
    boolean existsByName(String name);

    @Override
    List<School> findAll();

    List<School> findByNameContaining(String name);

    // 检查学校是否有相关学生
    @Query("SELECT COUNT(s) > 0 FROM Student s WHERE s.school.id = :schoolId")
    boolean existsStudentsBySchoolId(Long schoolId);
}
