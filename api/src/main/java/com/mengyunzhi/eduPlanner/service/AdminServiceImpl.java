package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.AdminRequest;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Admin;
import com.mengyunzhi.eduPlanner.entity.User;
import com.mengyunzhi.eduPlanner.repository.AdminRepository;
import com.mengyunzhi.eduPlanner.repository.UserRepository;
import com.mengyunzhi.eduPlanner.service.validator.AdminValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private AdminRepository adminRepository;

    @Autowired
    private AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminValidator adminValidator;

    @Override
    public Response<Void> save(AdminRequest adminRequest) {
//        adminValidator.validateAno(adminRequest.getAno().toString());

        if (adminRepository.existsByAno(adminRequest.getAno())) {
            return new Response<>(false, "该工号已存在", null);
        }
        // 创建 User 对象
        User user = new User();
        user.setUsername(adminRequest.getUsername());
        user.setPassword(adminRequest.getAno());
        user.setRole(adminRequest.getRole());

        user = userRepository.save(user);

        Admin admin = new Admin();
        admin.setName(adminRequest.getName());
        admin.setAno(adminRequest.getAno());
        admin.setUser(user);

        adminRepository.save(admin);
        return new Response<>(true, "新增成功", null);
    }

    @Override
    public List<Admin> getAll() {
        return this.adminRepository.findAll();
    }
}
