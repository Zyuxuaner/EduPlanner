package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Clazz;
import com.mengyunzhi.eduPlanner.service.ClazzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("Clazz")
public class ClazzController {
    private static final Logger logger = LoggerFactory.getLogger(ClazzController.class);

    @Autowired
    ClazzService clazzService;

    @GetMapping("/getAll")
    public List<Clazz> getAll() {
        return this.clazzService.getAll();
    }

    @GetMapping("/getClazzBySchoolId/{schoolId}")
    public List<Clazz> getClazzBySchoolId(@PathVariable Long schoolId) {
        return this.clazzService.getClazzBySchoolId(schoolId);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> save(@RequestBody Clazz clazz) {
       return clazzService.save(clazz);
    }
}
