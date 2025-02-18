package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.CourseInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CourseInfoRepository extends CrudRepository<CourseInfo, Long> {
    CourseInfo findByCourseId(Long courseId);

    /**
     * 根据课程id查询课程安排
     * @param courseId 课程id
     * @return 课程安排列表
     */
    List<CourseInfo> findAllByCourseId(Long courseId);

    List<CourseInfo> findByStudentId(Long studentId);
//    @Query("SELECT ci FROM CourseInfo ci " +
//            "JOIN ci.course c " +
//            "JOIN c.term t " +
//            "WHERE ci.day = :day " + // 明天的星期几
//            "AND t.startTime <= :endTime " + // 学期开始时间在明天结束时间之前
//            "AND t.endTime >= :startTime " + // 学期结束时间在明天开始时间之后
//            "AND ci.begin <= :end " + // 课程开始时间在指定节次内
//            "AND (ci.begin + ci.length) > :begin " + // 课程结束时间在指定节次内
//            "AND c.studentId = :studentId") // 指定学生的id
//    List<CourseInfo> findCoursesByTomorrow(@Param("startTime") Date startTime,
//                                           @Param("endTime") Date endTime,
//                                           @Param("day") Long day,
//                                           @Param("begin") Long begin,
//                                           @Param("end") Long end,
//                                           @Param("studentId") Long studentId);
}
