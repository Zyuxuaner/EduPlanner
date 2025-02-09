package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    public Response<Void> save(@RequestBody School school) {
        return this.schoolService.save(school);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<School>> getSchoolById(@PathVariable Long id) {
        Optional<School> optionalSchool = schoolService.getSchoolById(id);
        return optionalSchool.map(school -> new ResponseEntity<>(Collections.singletonList(school), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/{id}")
    public ResponseEntity<School> updateSchool(@PathVariable Long id, @RequestBody String name) {
        try {
            School updatedSchool = schoolService.updateSchool(id, name);
            return new ResponseEntity<>(updatedSchool, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
