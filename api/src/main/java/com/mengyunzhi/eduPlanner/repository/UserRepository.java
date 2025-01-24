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

    /**
     * 根据id查找用户，若不存在则返回null
     * @param id 用户id
     * @return User对象或null
     */
    default User findByIdOrNull(Long id) {
        return findById(id).orElse(null);
    }
}
