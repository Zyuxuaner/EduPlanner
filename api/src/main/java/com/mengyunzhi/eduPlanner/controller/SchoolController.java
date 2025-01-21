package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.repository.SchoolRepository;
import com.mengyunzhi.eduPlanner.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("School")
public class SchoolController {
    private final static Logger logger = Logger.getLogger(SchoolController.class.getName());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SchoolService schoolService;

    @GetMapping("/getAll")
    public List<School> getAll() {
        return this.schoolService.getAll();
    }

    @PostMapping
    @RequestMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody School school) {
        this.schoolService.save(school);
    }
}
