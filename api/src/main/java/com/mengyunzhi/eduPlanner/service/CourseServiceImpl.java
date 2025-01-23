package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseRequest;
import com.mengyunzhi.eduPlanner.entity.*;
import com.mengyunzhi.eduPlanner.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Term activeTerm = termRepository.findBySchool_idAndStatus(schoolId, 1L)
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
}
