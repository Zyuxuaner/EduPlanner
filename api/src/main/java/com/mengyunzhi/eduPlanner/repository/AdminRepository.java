package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.Admin;
import com.mengyunzhi.eduPlanner.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Long> {
    Admin findByUserId(Long userId);

    @Override
    List<Admin> findAll();

    boolean existsByAno(String ano);

    @Query("SELECT a FROM Admin a WHERE (:name IS NULL OR a.name LIKE %:name%) AND (:ano IS NULL OR a.ano LIKE %:ano%)")
    List<Admin> searchAdmins(@Param("name") String name, @Param("ano") String ano);
}
