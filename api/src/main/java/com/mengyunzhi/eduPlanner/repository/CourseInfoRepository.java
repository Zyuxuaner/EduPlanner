package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.CourseInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseInfoRepository extends CrudRepository<CourseInfo, Long> {
    CourseInfo findByCourseId(Long courseId);

    /**
     * 根据课程id查询课程安排
     * @param courseId 课程id
     * @return 课程安排列表
     */
    List<CourseInfo> findAllByCourseId(Long courseId);
}
