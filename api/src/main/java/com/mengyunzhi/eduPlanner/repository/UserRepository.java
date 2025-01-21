package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * 查找用户
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);

    User findById(long id);
}
