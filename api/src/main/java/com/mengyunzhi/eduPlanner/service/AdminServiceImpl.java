package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.AdminRequest;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Admin;
import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.entity.User;
import com.mengyunzhi.eduPlanner.repository.AdminRepository;
import com.mengyunzhi.eduPlanner.repository.UserRepository;
import com.mengyunzhi.eduPlanner.service.validator.AdminValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Response<Void> delete(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (!admin.isPresent()) {
            return new Response<>(false, "管理员不存在", null);
        }

        Long userId = admin.get().getUser().getId();
        if (!userRepository.existsById(userId)) {
            return new Response<>(false, "用户不存在", null);
        }

        try {
            this.adminRepository.deleteById(id);
            this.userRepository.deleteById(userId);
            return new Response<>(true, "删除成功", null);
        } catch (Exception e) {
            return new Response<>(false, "删除失败: " + e.getMessage(), null);
        }
    }

    @Override
    public Response<Admin> resetPassword(Long id, String newPassword) {
        // 根据 ID 查找管理员
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            // 获取管理员对应的用户
            if (admin.getUser() != null) {
                User user = admin.getUser();
                // 更新用户的密码
                user.setPassword(newPassword);
                // 保存更新后的用户信息到数据库
                userRepository.save(user);
                return new Response<>(true, "密码重置成功", admin);
            } else {
                return new Response<>(false, "管理员对应的用户信息为空，无法重置密码", null);
            }
        } else {
            return new Response<>(false, "未找到对应的管理员信息", null);
        }
    }
}
