package com.mengyunzhi.eduPlanner.service;


import com.mengyunzhi.eduPlanner.dto.StudentRequest;
import com.mengyunzhi.eduPlanner.entity.Student;

import java.util.List;

public interface StudentService {

    /**
     * 新增学生
     * @param studentRequest 从前台传过来的数据类型
     * @return
     */
    Student save(StudentRequest studentRequest);

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
}
