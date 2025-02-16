package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl implements SchoolService {
    private final static Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);

    private final SchoolRepository schoolRepository;

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
    public ResponseEntity<List<School>> getSchoolById(Long id) {
        Optional<School> optionalSchool = schoolRepository.findById(id);
        return optionalSchool.map(school -> new ResponseEntity<>(Collections.singletonList(school), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public List<School> searchSchoolsByName(String name) {
        return schoolRepository.findByNameContaining(name);
    }

    @Override
    public Response<School> updateSchool(Long id, String name) {
        try {
            // 尝试根据 id 查找学校
            Optional<School> schoolOptional = schoolRepository.findById(id);
            // 如果找到了学校，获取该学校对象；如果没找到，抛出异常
            School school = schoolOptional.orElseThrow(() ->
                    new RuntimeException("无id为 " + id + " 的学校")
            );
            // 更新学校的名称
            school.setName(name);
            // 保存更新后的学校到数据库
            School updatedSchool = schoolRepository.save(school);
            return new Response<>(true, "编辑成功", updatedSchool);
        } catch (RuntimeException e) {
            return new Response<>(false, "编辑失败：" + e.getMessage(), null);
        }
    }

    @Override
    public Response<Void> deleteSchool(Long id) {
        schoolRepository.deleteById(id);
        return new Response<>(true,"删除成功",null);
    }
}
