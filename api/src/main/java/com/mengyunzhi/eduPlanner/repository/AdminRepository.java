package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUserId(Long userId);

    @Override
    List<Admin> findAll();

    boolean existsByAno(String ano);

    /**
     * 根据管理员姓名和编号进行模糊查询
     * @param name 管理员姓名，用于模糊查询
     * @param ano 管理员编号，用于模糊查询
     * @return 符合条件的管理员列表
     */
    List<Admin> findByNameContainingAndAnoContaining(String name, String ano);

    /**
     * 仅根据管理员姓名进行模糊查询
     * @param name 管理员姓名，用于模糊查询
     * @return 符合条件的管理员列表
     */
    List<Admin> findByNameContaining(String name);

    /**
     * 仅根据管理员编号进行模糊查询
     * @param ano 管理员编号，用于模糊查询
     * @return 符合条件的管理员列表
     */
    List<Admin> findByAnoContaining(String ano);
}
