package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    @Override
    List<Admin> findAll();

    boolean existsByAno(String ano);
}
