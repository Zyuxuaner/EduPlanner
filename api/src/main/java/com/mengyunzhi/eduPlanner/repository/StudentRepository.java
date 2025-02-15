package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Student findByUserId(Long userId);

    void deleteByUserId(Long userId);

//    List<Student> findAllByClazzIdAndStatus(Long clazzId, Long status);

    Student findByIdAndStatus(Long studentId, Long status);

    @Override
    List<Student> findAll();

    List<Student> findByStatus(Long status);

    boolean existsBySno(String sno);

    List<Student> findAll(Specification<Student> spec);

}
