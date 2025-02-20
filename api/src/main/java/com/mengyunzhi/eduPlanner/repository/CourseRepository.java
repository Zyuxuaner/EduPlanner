package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author zhangyuxuan
 */
public interface CourseRepository extends CrudRepository<Course, Long> {

    Course findByNameAndTermId(String name, Long termId);

    /**
     * 查询学期下所有 course
     * @param termId 学期id
     * @return courseList
     */
    List<Course> findAllByTermId(Long termId);

    boolean existsByTermId(Long termId);

//    List<Course> findByClazzIdAndTermIdAndType(Long clazzId, Long termId, Long type);
//    List<Course> findByStudentIdAndTermIdAndType(Long studentId, Long termId, Long type);
//
//    List<Course> findByClazzIdAndType(Long clazzId, Long type);
//
//    List<Course> findByStudentIdAndType(Long studentId, Long type);
//    List<Course> findByTermIdAndClazzId(Long termId, Long clazzId);

}
