package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.entity.User;

public interface UserService {

    /**
     *
     * @param id
     * @return
     */
    User findById(Long id);

    /**
     *
     * @param user
     * @return
     */
    User save(User user);
}
