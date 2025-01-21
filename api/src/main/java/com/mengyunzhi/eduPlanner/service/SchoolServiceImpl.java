package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public School save(School school) {
        return this.schoolRepository.save(school);
    }

    @Override
    public List<School> getAll() {
        return this.schoolRepository.findAll();
    }
}
