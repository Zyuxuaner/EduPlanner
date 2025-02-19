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
        return Response.success("课程新增成功");
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

//
//    /**
//     * 获取所有学校的当前周的课程安排（激活学期）
//     * @param schoolId 学校id数组
//     * @param weeks 当前周 数组
//     * @return 嵌套数据
//     */
//    @GetMapping("/getAllCourseInfo")
//    public Response<Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>>> getAllCourseInfo(
//            @RequestParam List<Long> schoolId,
//            @RequestParam List<Long> weeks) {
//        Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> allStudentCourseData = new HashMap<>();
//
//        // 遍历 schoolId 和 weeks 进行处理
//        for (int i = 0; i < schoolId.size(); i++) {
//            Long schoolIdValue = schoolId.get(i);
//            Long weekValue = weeks.get(i);
//
//            Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> studentCourseData =
//                    this.courseService.getAllStudentsCoursesOfSchool(schoolIdValue, weekValue);
//
//            allStudentCourseData.putAll(studentCourseData);
//        }
//        return new Response<>(true, "成功获取所有学生课程信息", allStudentCourseData);
//    }
//
//    /**
//     * 根据 week，来查询当前学生在 week 周下的课程安排
//     * @param week 查询的周数
//     * @return
//     */
//    @GetMapping("/getCourseInfoByStudent")
//    public Response<Map<Long, Map<Long, List<CourseDto.StudentCourseInfoResponse>>>> getCourseInfoByStudent(@RequestParam Long week) {
//        Response<CurrentUser> currentUser = loginService.getCurrentLoginUser();
//        Long schoolId = currentUser.getData().getSchoolId();
//        Long userId = currentUser.getData().getId();
//        Student student = studentService.findByUserId(userId);
//        Long clazzId = student.getClazz().getId();
//        Long studentId = student.getId();
//        // 定义激活学期的状态值
//        Long ACTIVE_STATUS = 1L;
//        // 获取当前学校激活学期id
//        Optional<Term> optTerm = this.termService.checkTermActive(schoolId, ACTIVE_STATUS);
//
//        Long termId = optTerm.get().getId();
//
//        Map<Long, Map<Long, List<CourseDto.StudentCourseInfoResponse>>> courseInfoResponse =
//                this.courseService.getCourseInfoByCurrentUserOfWeek(clazzId, studentId, week, termId);
//
//        return new Response<>(true, "成功获取所选周的课程安排", courseInfoResponse);
//    }
}
