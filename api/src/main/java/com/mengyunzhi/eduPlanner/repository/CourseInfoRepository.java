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

    /**
     * 判断改学生是否还存在自己创建的课程安排
     * @param creatorId 学生id
     * @return 创建个数
     */
    Long countByCreatorId(Long creatorId);

    /**
     * 判断该学生是否还存在复用课程的情况
     * @param studentId 该学生id
     * @return 复用个数
     */
    Long countByStudentsId(Long studentId);

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

    List<CourseInfo> findByDayAndCreator(Long day, Student creator);

    /**
     * 根据课程名称模糊查询和创建者学生 ID 精确查询课程信息
     * @param searchCourse 课程名称，用于模糊查询
     * @param creatorStudent 创建者学生 ID，用于精确查询
     * @return 符合条件的课程信息列表
     */
    List<CourseInfo> findByCourseNameContainingAndCreatorId(String searchCourse, Long creatorStudent);

    /**
     * 仅根据课程名称模糊查询课程信息
     * @param searchCourse 课程名称，用于模糊查询
     * @return 符合条件的课程信息列表
     */
    List<CourseInfo> findByCourseNameContaining(String searchCourse);

    /**
     * 根据创建者学生 ID 精确查询课程信息
     * @param creatorStudent 创建者学生 ID，用于精确查询
     * @return 符合条件的课程信息列表
     */
    List<CourseInfo> findByCreatorId(Long creatorStudent);

}
