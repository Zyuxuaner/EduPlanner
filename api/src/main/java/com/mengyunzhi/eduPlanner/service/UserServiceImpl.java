package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.User;
import com.mengyunzhi.eduPlanner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findByIdOrNull(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Response<String> changePassword(Long userId, String oldPassword, String newPassword) {
        // 根据用户 ID 查找用户
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new Response<>(false, "用户不存在", null);
        }

        // 验证旧密码
        if (!user.getPassword().equals(oldPassword)) {
            return new Response<>(false, "旧密码错误", null);
        }

        // 更新密码
        user.setPassword(newPassword);
        userRepository.save(user);

        return new Response<>(true, "密码修改成功", null);
    }

}
