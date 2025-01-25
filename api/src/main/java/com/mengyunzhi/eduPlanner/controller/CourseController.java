package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.CourseRequest;
import com.mengyunzhi.eduPlanner.dto.CourseResponse;
import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.service.CourseService;
import com.mengyunzhi.eduPlanner.service.LoginService;
import com.mengyunzhi.eduPlanner.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public Response<Void> add(@RequestBody CourseRequest courseRequest) {

        if (!courseService.isTimeLegal(courseRequest)) {
            return new Response<>(false, "课程时间冲突，请重新添加", null);
        }

        Response<CurrentUser> currentUser = loginService.getCurrentLoginUser();
        logger.info("currentUser:" + currentUser);
        Long userId = currentUser.getData().getId();
        Long schoolId = currentUser.getData().getSchoolId();

        courseService.save(courseRequest, userId, schoolId);
        return new Response<>(true, "课程新增成功", null);
    }

    /**
     * 根据当前登录用户的 clazzId 和 studentId 获取有关该用户的全部课程
     * @return List<CourseResponse>
     */
    @GetMapping("/getAll")
    public List<CourseResponse> getAllCoursesForCurrentUser() {
        Response<CurrentUser> currentUser = loginService.getCurrentLoginUser();
        logger.info("currentUser:" + currentUser);
        Long userId = currentUser.getData().getId();
        Student student = studentService.findByUserId(userId);
        Long studentId = student.getId();
        Long clazzId = student.getClazz().getId();
        return courseService.getAllCoursesForCurrentUser(clazzId, studentId);
    }
}
