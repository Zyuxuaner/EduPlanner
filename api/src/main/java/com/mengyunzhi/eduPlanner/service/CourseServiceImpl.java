package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseDto;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.*;
import com.mengyunzhi.eduPlanner.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Course save(CourseDto.SaveRequest saveRequest, Long userId, Long schoolId) {
        Term activeTerm = termRepository.findBySchoolIdAndStatus(schoolId, 1L)
                .orElseThrow(() -> new IllegalArgumentException("该学校下没有激活学期"));

        Student student = studentRepository.findByUserId(userId);

        Long clazzId = student.getClazz().getId();
        Long studentId = null;
        if (saveRequest.getType().equals(1L)) {
            studentId = student.getId();
        }

        School school = student.getClazz().getSchool();

        Course course = new Course();
        course.setName(saveRequest.getName());
        course.setType(saveRequest.getType());
        course.setTerm(activeTerm);
        course.setClazz(new Clazz(clazzId, school));
        course.setStudentId(studentId);
        Course savedCourse = courseRepository.save(course);

        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setStartWeek(saveRequest.getStartWeek());
        courseInfo.setEndWeek(saveRequest.getEndWeek());
        courseInfo.setType(saveRequest.getStatus());
        courseInfo.setDay(saveRequest.getWeek());
        courseInfo.setBegin(saveRequest.getBegin());
        courseInfo.setLength(saveRequest.getEnd() - saveRequest.getBegin() + 1);
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
    public List<CourseDto.GetAllCoursesForCurrentUserResponse> getAllCoursesForCurrentUser(Long clazzId, Long studentId) {
        List<Course> requiredCourses = courseRepository.findByClazzIdAndType(clazzId, 2L);
        List<Course> electiveCourses = courseRepository.findByStudentIdAndType(studentId, 1L);

        List<Course> allCourses = new ArrayList<>();
        allCourses.addAll(requiredCourses);
        allCourses.addAll(electiveCourses);

        List<CourseDto.GetAllCoursesForCurrentUserResponse> courseResponses = new ArrayList<>();
        for (Course course : allCourses) {
            CourseDto.GetAllCoursesForCurrentUserResponse courseResponse = new CourseDto.GetAllCoursesForCurrentUserResponse();
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

    @Override
    public Response<Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>>> getAllStudentsCoursesOfSchool(Long schoolId, Long week) {
        List<Clazz> clazzList = clazzRepository.findClazzBySchoolId(schoolId);

        // 用来存储返回的数据结构
        Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> studentCourseData = new HashMap<>();

        for (Clazz clazz : clazzList) {
            // 获取该班级的所有必修课
            List<Course> requiredCourses = courseRepository.findByClazzIdAndType(clazz.getId(), 2L);
            // 获取该班级的所有选修课
            List<Course> electiveCourses = courseRepository.findByStudentIdAndType(clazz.getId(), 1L);

            // 处理必修课
            for (Course course : requiredCourses) {
                processCourse(clazz, course, week, studentCourseData);
            }
            // 处理选修课
            for (Course course : electiveCourses) {
                processCourse(clazz, course, week, studentCourseData);
            }
        }

        return new Response<>(true, "成功获取学生课程信息", studentCourseData);
    }

    /**
     * 获取对应课程的课程安排
     * 只获取符合当前 week 的课程安排
     * 根据课程类型获取激活状态学生列表，必修课：获取该班级的所有学生；选修课：获取对应的学生
     * @param clazz 班级
     * @param course 课程
     * @param week 第几周
     * @param studentCourseData 返回的课程数据
     */
    private void processCourse(Clazz clazz, Course course, Long week, Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> studentCourseData) {
        List<CourseInfo> courseInfos = courseInfoRepository.findAllByCourseId(course.getId());

        for (CourseInfo courseInfo : courseInfos) {
            // 如果当前课程的开始和结束周包含了目标周
            if (courseInfo.getStartWeek() <= week && courseInfo.getEndWeek() >= week) {
                List<String> students = new ArrayList<>();

                // 必修课获取学生名单
                if (course.getType() == 2) {
                    List<Student> clazzStudents = studentRepository.findAllByClazzIdAndStatus(clazz.getId(), 1L);
                    for (Student student : clazzStudents) {
                        students.add(student.getName());
                    }
                    // 选修课：获取指定学生
                } else if (course.getType() == 1 && course.getStudentId() != null) {
                    Student student = studentRepository.findByIdAndStatus(course.getStudentId(), 1L);
                    if (student != null) {
                        students.add(student.getName());
                    }
                }

                // 构建课程安排数据
                CourseDto.StudentsCoursesOfSchoolResponse response = new CourseDto.StudentsCoursesOfSchoolResponse();
                response.setWeek(courseInfo.getDay());
                response.setBegin(courseInfo.getBegin());
                response.setLength(courseInfo.getLength());
                response.setStudents(students);

                // 按照课程 ID 和天（day）将课程安排按需要的结构添加到 studentCourseData
                studentCourseData
                        .computeIfAbsent(course.getId(), k -> new HashMap<>())
                        .computeIfAbsent(courseInfo.getDay(), k -> new ArrayList<>())
                        .add(response);
            }
        }
    }
}
