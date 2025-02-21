package com.mengyunzhi.eduPlanner.service;


import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.dto.StudentRequest;
import com.mengyunzhi.eduPlanner.entity.Student;

import java.util.List;

public interface StudentService {

    /**
     * 新增学生
     * @param studentRequest 从前台传过来的数据类型
     * @return
     */
    Response<Void> save(StudentRequest studentRequest);

    /**
     * 获取所有学生
     * @return
     */
    List<Student> getAll();

    /**
     * 根据 userId 获取学生
     * @param userId
     * @return Student
     */
    Student findByUserId(Long userId);

    Response<String> delete (Long id);

    Student changeStatus(Long id, Long status);

    Response<Student> resetPassword(Long id, String newPassword);

    List<Student> search(Long schoolId, String searchName, String searchStudentSno);

    Student getStudentById(Long id);

    Response<String> updateStudent(Long id, StudentRequest studentRequest);
}
