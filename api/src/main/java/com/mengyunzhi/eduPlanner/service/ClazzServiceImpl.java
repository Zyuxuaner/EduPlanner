package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.entity.Clazz;
import com.mengyunzhi.eduPlanner.repository.ClazzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    ClazzRepository clazzRepository;

    @Override
    public List<Clazz> getAll() {
        return this.clazzRepository.findAll();
    }

    @Override
    public void save(Clazz clazz) {
        this.clazzRepository.save(clazz);
    }
}
