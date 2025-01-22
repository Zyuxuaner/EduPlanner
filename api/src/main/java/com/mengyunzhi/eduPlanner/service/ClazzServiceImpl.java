package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.controller.ClazzController;
import com.mengyunzhi.eduPlanner.dto.ClazzRequest;
import com.mengyunzhi.eduPlanner.entity.Clazz;
import com.mengyunzhi.eduPlanner.entity.School;
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
    public void save(ClazzRequest clazzRequest) {
        logger.info("Received ClazzRequest: {}", clazzRequest);
        // 创建 Clazz 对象
        Clazz clazz = new Clazz();
        clazz.setName(clazzRequest.getClazz());

        // 创建 School 对象并设置 id
        School school = new School();
        school.setId(clazzRequest.getSchool_id());
        clazz.setSchool(school);

        // 保存 Clazz 对象
        this.clazzRepository.save(clazz);
        logger.info("Clazz saved successfully: {}", clazz);
    }
}
