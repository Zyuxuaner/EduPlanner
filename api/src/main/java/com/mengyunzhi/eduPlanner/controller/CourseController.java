package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.*;
import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.entity.Term;
import com.mengyunzhi.eduPlanner.repository.StudentRepository;
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
    private final StudentRepository studentRepository;

    @Autowired
    public CourseController(LoginService loginService, CourseService courseService, StudentService studentService, StudentRepository studentRepository) {
        this.loginService = loginService;
        this.courseService = courseService;
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<String> add(@RequestBody CourseDto.SaveRequest saveRequest) {


        Response<CurrentUser> currentUser = loginService.getCurrentLoginUser();
        Long userId = currentUser.getData().getId();
        Long schoolId = currentUser.getData().getSchoolId();

        return courseService.save(saveRequest, userId, schoolId);
    }

    @DeleteMapping("/delete/{courseInfoId}")
    public Response<String> delete(@PathVariable Long courseInfoId) {
        return courseService.delete(courseInfoId);
    }

    @GetMapping("/checkReuse/{courseInfoId}")
    public Response<String> checkReuse(@PathVariable Long courseInfoId) {
        return courseService.checkReuseStudent(courseInfoId);
    }

    /**
     * 获取全部课程
     * @return List<CourseResponse>
     */
    @GetMapping("/getAll")
    public List<CourseDto.GetAllCoursesResponse> getAllCoursesForCurrentUser() {
        return this.courseService.getAllCourses();
    }

    @GetMapping("/getCourseInfoById/{courseInfoId}")
    public Response<CourseDto.GetCourseInfoByIdResponse> getCourseInfoById(@PathVariable Long courseInfoId) {
        return this.courseService.getCourseInfoById(courseInfoId);
    }

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

    /**
     * 获取所有学校的当前周的课程安排（激活学期）
     * @param schoolId 学校id数组
     * @param weeks 当前周 数组
     * @return 嵌套数据
     */
    @GetMapping("/getAllCourseInfo")
    public Response<Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> getAllMessage(
            @RequestParam List<Long> schoolId,
            @RequestParam List<Long> weeks) {
        Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> allStudentCourseInfos =
                this.courseService.getAllMessage(schoolId, weeks);
        return Response.success(allStudentCourseInfos, "成功获取所有学生课程信息");
    }

    @GetMapping("/search")
    public List<CourseDto.GetAllCoursesResponse> search(
            @RequestParam(required = false) String searchCourse,
            @RequestParam(required = false) Long creatorStudent
    ) {
        return courseService.search(searchCourse, creatorStudent);
    }

    @PostMapping("/update/{courseInfoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<String> update(@RequestBody CourseDto.SaveRequest saveRequest, @PathVariable Long courseInfoId) {
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();
        Long role = currentUser.getData().getRole();
        if (role == 1) {
            Long userId = currentUser.getData().getId();
            Student currentStudent = this.studentRepository.findByUserId(userId);
            return this.courseService.update(saveRequest, courseInfoId, currentStudent.getId());
        } else {
            return Response.fail("未获取到当前登录学生，编辑失败");
        }

    }
}
