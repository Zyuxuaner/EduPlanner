package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.entity.Term;
import com.mengyunzhi.eduPlanner.repository.TermRepository;
import com.mengyunzhi.eduPlanner.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("Term")
public class TermController {
    private final static Logger logger = Logger.getLogger(TermController.class.getName());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TermService termService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Term term) {
         this.termService.save(term);
    }

    @GetMapping("/getAll")
    public List<Term> getAll() {
        return this.termService.getAll();
    }
}
