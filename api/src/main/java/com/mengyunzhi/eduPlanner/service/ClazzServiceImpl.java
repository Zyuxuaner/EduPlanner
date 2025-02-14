package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.controller.ClazzController;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.Clazz;
import com.mengyunzhi.eduPlanner.entity.School;
import com.mengyunzhi.eduPlanner.repository.ClazzRepository;
import com.mengyunzhi.eduPlanner.repository.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClazzServiceImpl implements ClazzService {
    private static final Logger logger = LoggerFactory.getLogger(ClazzController.class);

    @Autowired
    private final ClazzRepository clazzRepository;

    @Autowired
    private final SchoolRepository schoolRepository;

    @Autowired
    private ClazzServiceImpl(ClazzRepository clazzRepository, SchoolRepository schoolRepository) {
        this.clazzRepository = clazzRepository;
        this.schoolRepository = schoolRepository;

    }

    @Override
    public Response<Void> deleteClazz(Long id) {
        clazzRepository.deleteById(id);
        return new Response<>(true,"删除成功",null);
    }

    @Override
    public List<Clazz> getAll() {
        return this.clazzRepository.findAll();
    }

    @Override
    public List<Clazz> getClazzBySchoolId(Long schoolId) {
        return clazzRepository.findBySchoolId(schoolId);
    }

    @Override
    public Clazz getClazzByClazzId(Long clazzId) {
        return clazzRepository.findById(clazzId).get();
    }

    @Override
    public Response<Void> save(Clazz clazz) {
        // 获取班级关联的学校 ID
        Long schoolId = clazz.getSchool().getId();
        String clazzName = clazz.getName();
        // 根据学校 ID 和班级名称查询是否存在重复的班级
        Optional<Clazz> existingClazz = clazzRepository.findBySchoolIdAndName(schoolId, clazzName);
        if (existingClazz.isPresent()) {
            return new Response<>(false, "该学校下该班级已存在", null);
        }
        // 若不存在重复班级，则保存班级信息
        this.clazzRepository.save(clazz);
        return new Response<>(true, "新增成功", null);
    }

    @Override
    public List<Clazz> searchClazzes(Long schoolId, String clazzName) {
        return clazzRepository.searchClazzes(schoolId, clazzName);
    }

    @Override
    public Response<Clazz> updateClazz(Long clazzId, Long schoolId, String name) {
        Optional<Clazz> optionalClazz = clazzRepository.findById(clazzId);
        if (optionalClazz.isPresent()) {
            Clazz clazz = optionalClazz.get();
            clazz.setName(name);
            // 根据 schoolId 查找 School 对象
            Optional<School> optionalSchool = schoolRepository.findById(schoolId);
            if (optionalSchool.isPresent()) {
                School school = optionalSchool.get();
                clazz.setSchool(school);
            } else {
                return new Response<>(false, "未找到对应的学校", null);
            }
            Clazz updatedClazz = clazzRepository.save(clazz);
            return new Response<>(true, "更新成功", updatedClazz);
        } else {
            return new Response<>(false, "未找到对应的班级", null);
        }
    }
}
