package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    private final static Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);

    private SchoolRepository schoolRepository;
    @Autowired
    private SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public Response<Void> save(School school) {
        if (schoolRepository.existsByName(school.getName())) {
            logger.info("学校姓名已存在");
            return new Response<>(false, "该学校已存在", null);
        }
        this.schoolRepository.save(school);
        return new Response<>(true, "新增成功", null);
    }

    @Override
    public List<School> getAll() {
        return this.schoolRepository.findAll();
    }
}
