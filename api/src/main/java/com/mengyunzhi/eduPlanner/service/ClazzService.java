package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Clazz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClazzService {
    Response<Void> deleteClazz(Long id);

    /**
     * 获取所有班级列表
     *
     */
    List<Clazz> getAll();

    /**
     *
     * @param schoolId
     * @return 根据学校返回所属的班级
     */
    List<Clazz> getClazzBySchoolId(Long schoolId);

    Clazz getClazzByClazzId(Long clazzId);

    Response<Clazz> updateClazz(Long clazzId, Long schoolId, String name);

    /**
     * 新增
     *
     * @param clazz 班级
     */
    Response<Void> save(Clazz clazz);

    List<Clazz> searchClazzes(Long schoolId, String clazzName);
}
