package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @DeleteMapping("/delete/{id}")
//    public Response<Void> deleteSchool(@PathVariable Long id) {
//        return this.schoolService.deleteSchool(id);
//    }

    @GetMapping("/getAll")
    public List<School> getAll() {
        return this.schoolService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<School>> getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    @PostMapping
    @RequestMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> save(@RequestBody School school) {
        return this.schoolService.save(school);
    }

    @GetMapping("/search")
    public List<School> searchSchools(@RequestParam("name") String name) {
        return schoolService.searchSchoolsByName(name);
    }

    @PutMapping("/{id}")
    public Response<School> updateSchool(@PathVariable Long id, @RequestBody String name) {
        return schoolService.updateSchool(id, name);
    }
}
