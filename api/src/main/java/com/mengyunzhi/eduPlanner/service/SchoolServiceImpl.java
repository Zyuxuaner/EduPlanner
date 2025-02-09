package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<School> getSchoolById(Long id) {
        return schoolRepository.findById(id);
    }

    @Override
    public School updateSchool(Long id, String name) {
        // 尝试根据 id 查找学校
        Optional<School> schoolOptional = schoolRepository.findById(id);
        // 如果找到了学校，获取该学校对象；如果没找到，抛出异常
        School school = schoolOptional.orElseThrow(() ->
                new RuntimeException("School not found with id: " + id)
        );
        // 更新学校的名称
        school.setName(name);
        // 保存更新后的学校到数据库
        return schoolRepository.save(school);
    }
}
