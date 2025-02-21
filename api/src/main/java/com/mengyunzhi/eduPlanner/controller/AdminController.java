package com.mengyunzhi.eduPlanner.controller;

import com.mengyunzhi.eduPlanner.dto.AdminRequest;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Admin;
import com.mengyunzhi.eduPlanner.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("Admin")
public class AdminController {
    private final static Logger logger = Logger.getLogger(AdminController.class.getName());

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

    @DeleteMapping("delete/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        return this.adminService.delete(id);
    }

    @PatchMapping("/resetPassword/{id}")
    public Response<Admin> resetPassword(@PathVariable Long id, @RequestBody String newPassword) {
        return this.adminService.resetPassword(id,newPassword);
    }

    @GetMapping("/search")
    public List<Admin> searchAdmins(@RequestParam(required = false) String name, @RequestParam(required = false) String ano) {
        return adminService.searchAdmins(name, ano);
    }

    @GetMapping("/getAdminById/{id}")
    public Admin getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    @PatchMapping("/update/{id}")
    public Response<String> updateTerm(@PathVariable Long id, @RequestBody AdminRequest adminRequest) {
        return this.adminService.updateAdmin(id, adminRequest);
    }
}
