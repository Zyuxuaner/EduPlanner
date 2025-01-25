package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.*;
import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.service.CourseService;
import com.mengyunzhi.eduPlanner.service.LoginService;
import com.mengyunzhi.eduPlanner.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("Course")
public class CourseController {
    private final static Logger logger = Logger.getLogger(CourseController.class.getName());

    private final CourseService courseService;
    private final LoginService loginService;
    private final StudentService studentService;

    @Autowired
    public CourseController(LoginService loginService, CourseService courseService, StudentService studentService) {
        this.loginService = loginService;
        this.courseService = courseService;
        this.studentService = studentService;
    }



    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> add(@RequestBody CourseDto.SaveRequest saveRequest) {
        if (!courseService.isTimeLegal(saveRequest)) {
            return new Response<>(false, "课程时间冲突，请重新添加", null);
        }

        Response<CurrentUser> currentUser = loginService.getCurrentLoginUser();
        logger.info("currentUser:" + currentUser);
        Long userId = currentUser.getData().getId();
        Long schoolId = currentUser.getData().getSchoolId();

        courseService.save(saveRequest, userId, schoolId);
        return new Response<>(true, "课程新增成功", null);
    }

    /**
     * 根据当前登录用户的 clazzId 和 studentId 获取有关该用户的全部课程
     * @return List<CourseResponse>
     */
    @GetMapping("/getAll")
    public List<CourseDto.GetAllCoursesForCurrentUserResponse> getAllCoursesForCurrentUser() {
        Response<CurrentUser> currentUser = loginService.getCurrentLoginUser();
        logger.info("currentUser:" + currentUser);
        Long userId = currentUser.getData().getId();
        Student student = studentService.findByUserId(userId);
        Long studentId = student.getId();
        Long clazzId = student.getClazz().getId();
        return courseService.getAllCoursesForCurrentUser(clazzId, studentId);
    }

    @GetMapping("/getAllStudentsCourse")
    public Response<Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>>> getAllStudentsCourse(@RequestParam Long schoolId, @RequestParam Long week) {
        Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> responseData = this.courseService.getAllStudentsCoursesOfSchool(schoolId, week);
        return new Response<>(true, "成功获取所有学生课程信息", responseData);
    }

    @GetMapping("/getAllCourseInfo")
    public Response<Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>>> getAllCourseInfo(
            @RequestParam List<Long> schoolId,
            @RequestParam List<Long> weeks) {
        Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> allStudentCourseData = new HashMap<>();

        // 遍历 schoolId 和 weeks 进行处理
        for (int i = 0; i < schoolId.size(); i++) {
            Long schoolIdValue = schoolId.get(i);
            Long weekValue = weeks.get(i);

            Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> studentCourseData =
                    this.courseService.getAllStudentsCoursesOfSchool(schoolIdValue, weekValue);

            allStudentCourseData.putAll(studentCourseData);
        }
        return new Response<>(true, "成功获取所有学生课程信息", allStudentCourseData);
    }
}
