package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.StudentRequest;
import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.entity.User;
import com.mengyunzhi.eduPlanner.repository.StudentRepository;
import com.mengyunzhi.eduPlanner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final UserRepository userRepository;

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Student save(StudentRequest studentRequest) {
        Long role = 1L;
        Long status = 1L;
        User user = new User();
        user.setUsername(studentRequest.getUsername());
        user.setPassword(studentRequest.getSno());
        user.setRole(role);

        user = userRepository.save(user);

        Student student = new Student();
        student.setName(studentRequest.getName());
        student.setSno(studentRequest.getSno());
        student.setStatus(status);
        student.setClazz(studentRequest.getClazz());
        student.setUser(user);

        return this.studentRepository.save(student);
    }

    @Override
    public List<Student> getAll() {
        return this.studentRepository.findAll();
    }

    @Override
    public Student findByUserId(Long userId) {
        return this.studentRepository.findByUserId(userId);
    }
}
