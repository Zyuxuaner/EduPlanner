package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.entity.Clazz;
import com.mengyunzhi.eduPlanner.repository.ClazzRepository;
import com.mengyunzhi.eduPlanner.service.ClazzService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("clazz")
public class ClazzController {
    private static final Logger logger = LoggerFactory.getLogger(ClazzController.class);

    @Autowired
    ClazzService clazzService;

    @Autowired
    ClazzRepository clazzRepository;

    @GetMapping("/getAll")
    public List<Clazz> getAll() {
        return this.clazzService.getAll();
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Clazz clazz) {
        clazzService.save(clazz);
    }
}
