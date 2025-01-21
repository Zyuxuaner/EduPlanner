package com.mengyunzhi.eduPlanner.service.validator;

import com.mengyunzhi.eduPlanner.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminValidator {
    @Autowired
    private AdminRepository adminRepository;

    public void validateAno(String ano) {
        // 校验 ano 是否为 null
        if (ano == null || ano.toString().length() != 6) {
            throw new IllegalArgumentException("ano 必须为 6 位数字");
        }

        // 校验 ano 是否已存在
        if (adminRepository.existsByAno(ano)) {
            throw new IllegalArgumentException("ano 已经存在，不能重复");
        }
    }
}
