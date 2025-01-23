package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseResponse;
import com.mengyunzhi.eduPlanner.dto.CourseRequest;
import com.mengyunzhi.eduPlanner.entity.*;
import com.mengyunzhi.eduPlanner.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private TermRepository termRepository;

    @Autowired
    private ClazzRepository clazzRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseInfoRepository courseInfoRepository;

    @Override
    public Course save(CourseRequest courseRequest, Long userId, Long schoolId) {
        Term activeTerm = termRepository.findBySchoolIdAndStatus(schoolId, 1L)
                .orElseThrow(() -> new IllegalArgumentException("该学校下没有激活学期"));

        Student student = studentRepository.findByUserId(userId);

        Long clazzId = student.getClazz().getId();
        Long studentId = null;
        if (courseRequest.getType().equals(1L)) {
            studentId = student.getId();
        }

        School school = student.getClazz().getSchool();

        Course course = new Course();
        course.setName(courseRequest.getName());
        course.setType(courseRequest.getType());
        course.setTerm(activeTerm);
        course.setClazz(new Clazz(clazzId, school));
        course.setStudentId(studentId);
        Course savedCourse = courseRepository.save(course);

        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setStartWeek(courseRequest.getStartWeek());
        courseInfo.setEndWeek(courseRequest.getEndWeek());
        courseInfo.setType(courseRequest.getStatus());
        courseInfo.setDay(courseRequest.getWeek());
        courseInfo.setBegin(courseRequest.getBegin());
        courseInfo.setLength(courseRequest.getEnd() - courseRequest.getBegin() + 1);
        courseInfo.setCourse(savedCourse);
        courseInfoRepository.save(courseInfo);

        return savedCourse;
    }

    /**
     * 首先获取该班级下的所有必修课
     * 接着获取该学生的所有选修课
     * 合并所有课程（选修 + 必修）
     * 转换成 CourseResponse 类型，并填充课程安排信息
     * @param clazzId 当前登录用户的班级id
     * @param studentId 当前登录用户的学生id
     * @return courseResponses
     */
    @Override
    public List<CourseResponse> getAllCoursesForCurrentUser(Long clazzId, Long studentId) {
        List<Course> requiredCourses = courseRepository.findByClazzIdAndType(clazzId, 2L);
        List<Course> electiveCourses = courseRepository.findByStudentIdAndType(studentId, 1L);

        List<Course> allCourses = new ArrayList<>();
        allCourses.addAll(requiredCourses);
        allCourses.addAll(electiveCourses);

        List<CourseResponse> courseResponses = new ArrayList<>();
        for (Course course : allCourses) {
            CourseResponse courseResponse = new CourseResponse();
            courseResponse.setName(course.getName());
            courseResponse.setType(course.getType());

            CourseInfo courseInfo = courseInfoRepository.findByCourseId(course.getId());
            if (courseInfo != null) {
                courseResponse.setWeekType(courseInfo.getType());
                courseResponse.setStartWeek(courseInfo.getStartWeek());
                courseResponse.setEndWeek(courseInfo.getEndWeek());
                courseResponse.setWeek(courseInfo.getDay());
                courseResponse.setBegin(courseInfo.getBegin());
                courseResponse.setLength(courseInfo.getLength());
            }

            courseResponse.setTerm(course.getTerm());

            courseResponses.add(courseResponse);
        }
        return courseResponses;
    }
}
