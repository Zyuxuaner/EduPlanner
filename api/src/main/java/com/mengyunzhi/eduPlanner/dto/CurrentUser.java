package com.mengyunzhi.eduPlanner.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回当前登录用户的详细信息
 */
public class CurrentUser {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Long role;

    @Getter
    @Setter
    private Long schoolId;

    public CurrentUser(Long id, String username, String password, Long role, Long schoolId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.schoolId = schoolId;
    }

    public CurrentUser(Long id, String username, String password, Long role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
