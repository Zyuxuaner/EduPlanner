package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.dto.StudentRequest;
import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.entity.User;
import com.mengyunzhi.eduPlanner.repository.SchoolRepository;
import com.mengyunzhi.eduPlanner.repository.StudentRepository;
import com.mengyunzhi.eduPlanner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {
    private final static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    private final SchoolRepository schoolRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository, SchoolRepository schoolRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.schoolRepository = schoolRepository;
    }

    @Override
    public Response<Void> save(StudentRequest studentRequest) {
        // 检查学号是否已存在
        if (studentRepository.existsBySno(studentRequest.getSno())) {
            return new Response<>(false, "该学号已存在", null);
        }

        // 获取学校信息
        Long schoolId = studentRequest.getSchool().getId();
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("未找到对应的学校信息"));

        // 创建用户
        Long role = 1L;
        Long status = 1L;
        User user = new User();
        user.setUsername(studentRequest.getUsername());
        user.setPassword(studentRequest.getSno());
        user.setRole(role);

        user = userRepository.save(user);

        // 创建学生
        Student student = new Student();
        student.setName(studentRequest.getName());
        student.setSno(studentRequest.getSno());
        student.setStatus(status);
        student.setUser(user);
        student.setSchool(school);

        // 保存学生信息
        this.studentRepository.save(student);
        return new Response<>(true, "新增成功", null);
    }

    @Override
    public List<Student> getAll() {
        return this.studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return this.studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student findByUserId(Long userId) {
        return this.studentRepository.findByUserId(userId);
    }

    @Override
    public Student changeStatus(Long id, Long status) {
        // 根据 ID 查找学生
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            // 如果找到学生，更新其状态
            Student student = optionalStudent.get();
            student.setStatus(status);
            // 保存更新后的学生信息到数据库
            return studentRepository.save(student);
        }
        // 如果未找到学生，返回 null
        return null;
    }

    @Override
    public Response<Student> resetPassword(Long id, String newPassword) {
        // 根据 ID 查找学生
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            // 获取学生对应的用户
            if (student.getUser() != null) {
                User user = student.getUser();
                // 更新用户的密码
                user.setPassword(newPassword);
                // 保存更新后的用户信息到数据库
                userRepository.save(user);
                return new Response<>(true, "密码重置成功", student);
            } else {
                return new Response<>(false, "学生对应的用户信息为空，无法重置密码", null);
            }
        } else {
            return new Response<>(false, "未找到对应的学生信息", null);
        }
    }

    @Override
    public List<Student> search(Long schoolId, Long clazzId, String searchName, String searchStudentSno) {
        Specification<Student> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (schoolId != null) {
                predicates.add(criteriaBuilder.equal(root.get("clazz").get("school").get("id"), schoolId));
            }

            if (clazzId != null) {
                predicates.add(criteriaBuilder.equal(root.get("clazz").get("id"), clazzId));
            }

            if (searchName != null && !searchName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + searchName + "%"));
            }

            if (searchStudentSno != null && !searchStudentSno.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("sno"), "%" + searchStudentSno + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // 使用构建好的 Specification 进行查询
        return studentRepository.findAll(spec);
    }

    @Transactional
    @Override
    public Response<Student> delete(Long id) {
        // 根据 ID 查找学生
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            // 获取学生对应的用户
            if (student.getUser() != null) {
                User user = student.getUser();
                // 先删除关联的学生记录
                studentRepository.deleteByUserId(user.getId());
                // 再删除用户记录
                userRepository.delete(user);
            } else {
                // 删除学生记录
                studentRepository.deleteById(id);
            }
            return new Response<>(true, "删除成功", null);
        }
        return new Response<>(false, "未找到对应的学生记录", null);
    }
}
