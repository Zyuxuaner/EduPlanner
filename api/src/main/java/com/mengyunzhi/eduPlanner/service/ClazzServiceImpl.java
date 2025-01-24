package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.controller.ClazzController;
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
    public void save(Clazz clazz) {
        this.clazzRepository.save(clazz);
    }
}
