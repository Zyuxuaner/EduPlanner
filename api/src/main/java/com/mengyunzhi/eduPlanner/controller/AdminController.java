package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.AdminRequest;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Admin;
import com.mengyunzhi.eduPlanner.entity.Term;
import com.mengyunzhi.eduPlanner.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("Admin")
public class AdminController {
    private final static Logger logger = Logger.getLogger(AdminController.class.getName());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AdminService adminService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> add(@RequestBody AdminRequest adminRequest) {
        return this.adminService.save(adminRequest);
    }

    @GetMapping("/getAll")
    public List<Admin> getAll() {
        return this.adminService.getAll();
    }
}
