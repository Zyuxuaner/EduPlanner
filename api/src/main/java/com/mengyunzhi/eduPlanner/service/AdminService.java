package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.AdminRequest;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Admin;

import java.util.List;

public interface AdminService {
    /**
     * 新增管理员
     * @param adminRequest
     * @return
     */
    Response<Void> save(AdminRequest adminRequest);

    /**
     * 获取所有管理员
     * @return
     */
    List<Admin> getAll();

    Response<Void> delete(Long id);

    Response<Admin> resetPassword(Long id, String newPassword);

    List<Admin> searchAdmins(String name, String ano);

    Admin getAdminById(Long id);

    Response<Admin> updateAdmin(Long id, AdminRequest adminRequest);
}
