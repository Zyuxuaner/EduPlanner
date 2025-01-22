package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.ClazzRequest;
import com.mengyunzhi.eduPlanner.entity.Clazz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClazzService {

    /**
     * 获取所有班级列表
     *
     */
    List<Clazz> getAll();

    /**
     * 新增
     *
     * @param clazzRequest 班级
     */
    void save(ClazzRequest clazzRequest);




}
