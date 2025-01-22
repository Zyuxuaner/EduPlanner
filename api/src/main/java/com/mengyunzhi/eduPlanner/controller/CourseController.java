package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.CourseRequest;
import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Course;
import com.mengyunzhi.eduPlanner.service.CourseService;
import com.mengyunzhi.eduPlanner.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("Course")
public class CourseController {
    private final static Logger logger = Logger.getLogger(CourseController.class.getName());

    @Autowired
    private CourseService courseService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody CourseRequest courseRequest) {
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();
        Long userId = currentUser.getData().getId();
        Long schoolId = currentUser.getData().getSchoolId();

        this.courseService.save(courseRequest, userId, schoolId);
    }

}
