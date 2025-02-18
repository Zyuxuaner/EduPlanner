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
        return this.studentService.changeStatus(id,status);
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
        return this.studentService.resetPassword(id,newPassword);
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
    public Response<Student> updateStudent(@PathVariable Long id, @RequestBody StudentRequest studentRequest) {
        try {
            Student updatedStudent = studentService.updateStudent(id, studentRequest);
            if (updatedStudent != null) {
                return new Response<>(true, "编辑成功", updatedStudent);
            } else {
                return Response.fail("未找到对应的学生信息，更新失败");
            }
        } catch (Exception e) {
            return Response.fail("更新学生信息时发生错误：" + e.getMessage());
        }
    }
}
