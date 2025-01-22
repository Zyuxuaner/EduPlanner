package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.ClazzRequest;
import com.mengyunzhi.eduPlanner.entity.Clazz;
import com.mengyunzhi.eduPlanner.entity.School;
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

    @GetMapping("/getAll")
    public List<Clazz> getAll() {
        logger.info("ClazzController.getAll() 方法被调用");
        return this.clazzService.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody ClazzRequest clazzRequest) {
        clazzService.save(clazzRequest);
    }
}
