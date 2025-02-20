package com.mengyunzhi.eduPlanner.repository;

import com.mengyunzhi.eduPlanner.entity.CourseInfo;
import com.mengyunzhi.eduPlanner.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author zhangyuxuan
 */
public interface CourseInfoRepository extends CrudRepository<CourseInfo, Long> {
    CourseInfo findByCourseId(Long courseId);

    @Override
    List<CourseInfo> findAll();

    /**
     * 根据 courseId 和 day 和 creatorId 查询该学生自己创建的课程安排
     * 用于方便数据验证
     * @param courseId 课程id（目的在于，查询该学期的课程安排）
     * @param day 星期数（用于将验证范围缩小）
     * @param creatorId 创建者的 studentid
     * @return 课程安排列表 courseInfoList
     */
    List<CourseInfo> findAllByCourseIdAndDayAndCreatorId(Long courseId, Long day, Long creatorId);

    /**
     * 根据 studentId 查找所有复用的 courseInfo
     * @param studentId 学生id
     * @return 课程安排列表 courseInfoList
     */
    List<CourseInfo> findAllByStudentsId(Long studentId);

    /**
     * 获取该学生所创建的所有课程安排 courseInfo
     * @param creator 创建者
     * @return courseInfoList
     */
    List<CourseInfo> findByCreator(Student creator);

    /**
     * 多对多关系，使用衍生查询，根据students的id进行查询
     * @param studentsId 学生id
     * @return courseInfo
     */
    List<CourseInfo> findByStudentsId(Long studentsId);
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
