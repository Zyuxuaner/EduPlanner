package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Term;
import com.mengyunzhi.eduPlanner.service.LoginService;
import com.mengyunzhi.eduPlanner.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("Term")
public class TermController {
    private final static Logger logger = Logger.getLogger(TermController.class.getName());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TermService termService;

    @Autowired
    LoginService loginService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Term term) {
         this.termService.save(term);
    }

    @GetMapping("/getAll")
    public List<Term> getAll() {
        return this.termService.getAll();
    }

    @GetMapping("/checkTerm")
    public Response<String> checkActive() {
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();
        Long schoolId = currentUser.getData().getSchoolId();
        Optional<Term> term = this.termService.checkTermActive(schoolId, 1L);
        if (term.isPresent()) {
            Response<String> response = new Response<>(true, "存在激活学期", "null");
            return response;
        } else {
            Response<String> response = new Response<>(false, "当前学校无激活学期", "null");
            return response;
        }
    }
}
