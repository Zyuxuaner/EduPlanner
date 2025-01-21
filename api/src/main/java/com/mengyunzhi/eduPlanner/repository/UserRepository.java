package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
