package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> findByClazzIdAndType(Long clazzId, Long type);
    List<Course> findByStudentIdAndType(Long studentId, Long type);
    List<Course> findByTermIdAndClazzId(Long termId, Long clazzId);
    Course findByNameAndTypeAndTermIdAndClazzIdAndStudentId(String name, Long type, Long termId, Long clazzId, Long studentId);
}
