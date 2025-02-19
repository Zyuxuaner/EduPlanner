package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.*;
import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.entity.Term;
import com.mengyunzhi.eduPlanner.service.CourseService;
import com.mengyunzhi.eduPlanner.service.LoginService;
import com.mengyunzhi.eduPlanner.service.StudentService;
import com.mengyunzhi.eduPlanner.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("Course")
public class CourseController {
    private final static Logger logger = Logger.getLogger(CourseController.class.getName());

    private final CourseService courseService;
    private final LoginService loginService;
    private final StudentService studentService;

    private final TermService termService;

    @Autowired
    public CourseController(LoginService loginService, CourseService courseService, StudentService studentService, TermService termService) {
        this.loginService = loginService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.termService = termService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<String> add(@RequestBody CourseDto.SaveRequest saveRequest) {


        Response<CurrentUser> currentUser = loginService.getCurrentLoginUser();
        Long userId = currentUser.getData().getId();
        Long schoolId = currentUser.getData().getSchoolId();

        courseService.save(saveRequest, userId, schoolId);
        return Response.success(null,"课程新增成功");
    }

    @DeleteMapping("/delete/{courseInfoId}")
    public Response<String> delete(@PathVariable Long courseInfoId) {
        return courseService.delete(courseInfoId);
    }

    /**
     * 获取全部课程
     * @return List<CourseResponse>
     */
    @GetMapping("/getAll")
    public List<CourseDto.GetAllCoursesResponse> getAllCoursesForCurrentUser() {
        return this.courseService.getAllCourses();
    }
//
    /**
     * 获取选中的学校、周数下的该学校所有学生的课程安排
     * 如果有学生id，则获取该学生的课程安排
     * @param schoolId 选中的学校id
     * @param week 选中的周数
     * @param studentId 选中的学生（非必须）
     * @return responseData
     */
    @GetMapping("/getCourseMessage")
    public Response<Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> getMessage(
            @RequestParam Long schoolId, @RequestParam Long week, @RequestParam(required = false) Long studentId) {
            Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> allStudentsMessage =
                    this.courseService.getMessage(schoolId, week, studentId);
            return new Response<>(true, "成功获取学生的课程信息", allStudentsMessage);
    }

    @PostMapping("/cancelReuse/{courseInfoId}")
    public Response<String> cancelReuseCourseInfo(@PathVariable Long courseInfoId) {
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();
        Long role = currentUser.getData().getRole();
        if (role != 1) {
            return Response.fail("获取当前登录学生失败");
        }
        Long userId = currentUser.getData().getId();
        Student currentStudent = this.studentService.findByUserId(userId);
        return this.courseService.cancelReuseCourseInfo(courseInfoId, currentStudent.getId());
    }

    @PostMapping("/reuse/{courseInfoId}")
    public Response<String> reuseCourseInfo(@PathVariable Long courseInfoId) {
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();
        Long role = currentUser.getData().getRole();
        if (role != 1) {
            return Response.fail("获取当前登录学生失败");
        }
        Long userId = currentUser.getData().getId();
        Student currentStudent = this.studentService.findByUserId(userId);
        return this.courseService.reuseCourseInfo(courseInfoId, currentStudent.getId());
    }
}
