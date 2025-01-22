package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.StudentRequest;
import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody StudentRequest studentRequest) {
         this.studentService.save(studentRequest);
    }

    @GetMapping("getAll")
    public List<Student> getAll() {
        return this.studentService.getAll();
    }
}
