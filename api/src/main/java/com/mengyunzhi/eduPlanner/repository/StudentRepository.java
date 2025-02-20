package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Student findByUserId(Long userId);

    void deleteByUserId(Long userId);

    Student findByIdAndStatus(Long studentId, Long status);

    @Override
    List<Student> findAll();

    List<Student> findByStatus(Long status);

    boolean existsBySno(String sno);

    List<Student> findAll(Specification<Student> spec);

    List<Student> findBySchoolId(Long schoolId);

    /**
     * 检查指定学校 ID 是否关联了学生
     * @param schoolId 学校的 ID
     * @return 如果存在关联学生返回 true，否则返回 false
     */
    boolean existsBySchoolId(Long schoolId);
}
