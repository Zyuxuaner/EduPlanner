package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.controller.ClazzController;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Clazz;
import com.mengyunzhi.eduPlanner.repository.ClazzRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    private static final Logger logger = LoggerFactory.getLogger(ClazzController.class);

    @Autowired
    ClazzRepository clazzRepository;

    @Override
    public List<Clazz> getAll() {
        return this.clazzRepository.findAll();
    }

    @Override
    public List<Clazz> getClazzBySchoolId(Long schoolId) {
        return clazzRepository.findBySchoolId(schoolId);
    }

    @Override
    public Response<Void> save(Clazz clazz) {
        if (!clazz.getName().endsWith("班")) {
            return new Response<>(false, "班级名称必须以“班”为结尾", null);
        }

        if (clazzRepository.existsByName(clazz.getName())) {
            return new Response<>(false, "该班级已存在", null);
        }

        this.clazzRepository.save(clazz);
        return new Response<>(true, "新增成功", null);
    }
}
