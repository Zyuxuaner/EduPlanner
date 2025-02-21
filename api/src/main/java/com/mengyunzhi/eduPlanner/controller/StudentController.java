package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.Response;
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

    @PutMapping("/changeStatus/{id}")
    public Student changeStatus(@PathVariable Long id, @RequestBody StudentRequest request) {
        Long status = request.getStatus();
        return this.studentService.changeStatus(id, status);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> save(@RequestBody StudentRequest studentRequest) {
        return this.studentService.save(studentRequest);
    }

    @GetMapping("/getAll")
    public List<Student> getAll() {
        return this.studentService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public Response<Student> delete(@PathVariable Long id) {
        if (id == null) {
            return new Response<>(false, "学生 ID 不能为空", null);
        }
        return studentService.delete(id);
    }

    @PatchMapping("/resetPassword/{id}")
    public Response<Student> resetPassword(@PathVariable Long id, @RequestBody String newPassword) {
        return this.studentService.resetPassword(id, newPassword);
    }

    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam(required = false) Long schoolId,
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) String searchStudentSno
    ) {
        return studentService.search(schoolId, searchName, searchStudentSno);
    }

    @GetMapping("/getStudentById/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PatchMapping("/update/{id}")
    public Response<String> updateStudent(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        return this.studentService.updateStudent(id, studentRequest);
    }
}
