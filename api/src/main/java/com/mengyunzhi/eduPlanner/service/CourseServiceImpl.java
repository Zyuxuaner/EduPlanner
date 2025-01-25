package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseResponse;
import com.mengyunzhi.eduPlanner.dto.CourseRequest;
import com.mengyunzhi.eduPlanner.dto.CurrentUser;
import com.mengyunzhi.eduPlanner.dto.Response;
import com.mengyunzhi.eduPlanner.entity.*;
import com.mengyunzhi.eduPlanner.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService{
    private final static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    @Autowired
    private TermRepository termRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseInfoRepository courseInfoRepository;

    @Autowired
    private LoginService loginService;

    private CourseInfo saveCourseInfo(CourseRequest courseRequest) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setStartWeek(courseRequest.getStartWeek());
        courseInfo.setEndWeek(courseRequest.getEndWeek());
        courseInfo.setType(courseRequest.getStatus());
        courseInfo.setDay(courseRequest.getWeek());
        courseInfo.setBegin(courseRequest.getBegin());
        courseInfo.setLength(courseRequest.getEnd() - courseRequest.getBegin() + 1);
        return courseInfo;
    }

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
        Course isExistCourse = courseRepository.findByNameAndTypeAndTermIdAndClazzIdAndStudentId(
                courseRequest.getName(),
                courseRequest.getType(),
                activeTerm.getId(),
                clazzId,
                studentId
        );
        if (isExistCourse != null) {
            CourseInfo courseInfo = saveCourseInfo(courseRequest);
            courseInfo.setCourse(isExistCourse);
            courseInfoRepository.save(courseInfo);
            return isExistCourse;
        } else {
            Course savedCourse = courseRepository.save(course);
            CourseInfo courseInfo = saveCourseInfo(courseRequest);
            courseInfo.setCourse(savedCourse);
            courseInfoRepository.save(courseInfo);
            return savedCourse;
        }
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

    @Override
    public Long getTermIdByLoginUser(Response<CurrentUser> currentUser) {
        Long schoolId = currentUser.getData().getSchoolId();
        Optional<Term> term = this.termRepository.findBySchoolIdAndStatus(schoolId, 1L);
        Long termId = term.get().getId();
        logger.info(String.valueOf(termId));
        return termId;
    }

    @Override
    public Long getClassIdByLoginUser(Response<CurrentUser> currentUser) {
        Long userId = currentUser.getData().getId();
        Student student = this.studentRepository.findByUserId(userId);
        return student.getClazz().getId();
    }

    @Override
    public boolean isTimeLegal(CourseRequest courseRequest) {
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();

        Long termId = this.getTermIdByLoginUser(currentUser);
        Long clazzId = this.getClassIdByLoginUser(currentUser);

        // courseRequest对应的courseInfo
        CourseInfo courseInfoRequest = saveCourseInfo(courseRequest);

        // 查询所有相关课程
        List<Course> courses = this.courseRepository.findByTermIdAndClazzId(termId, clazzId);

        for (Course existingCourse : courses) {
            // 获取已经存在的course对应的courseInfos
            Long courseId = existingCourse.getId();
            List<CourseInfo> courseInfos = this.courseInfoRepository.findCourseInfoByCourseId(courseId);
            for (CourseInfo existingCourseInfo : courseInfos) {
                if (isTimeConflict(courseInfoRequest, existingCourseInfo)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isTimeConflict(CourseInfo newCourseInfo, CourseInfo existingCourseInfo) {
        Set<Long> newWeeks = getWeeksInRange(newCourseInfo.getStartWeek(), newCourseInfo.getEndWeek(), newCourseInfo.getType());
        Set<Long> existingWeeks = getWeeksInRange(existingCourseInfo.getStartWeek(), existingCourseInfo.getEndWeek(), newCourseInfo.getType());

        if (newCourseInfo.getDay().equals(existingCourseInfo.getDay())) {
            // 合法情况：新课程的开始时间大于旧课程的结束时间 && 新课程的结束时间小于旧课程的开时间
            if (newCourseInfo.getBegin() < existingCourseInfo.getBegin() + existingCourseInfo.getLength() &&
                    newCourseInfo.getBegin() + newCourseInfo.getLength() > existingCourseInfo.getBegin()) {
                // 时间不合法，再检查周数是否重叠
                for (Long newWeek : newWeeks) {
                    if (existingWeeks.contains(newWeek)) {
                        // 如果周数有重叠，有时间冲突，返回true
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public Set<Long> getWeeksInRange(Long startWeek, Long endWeek, Long type) {
        Set<Long> weeks = new HashSet<>();

        for (long week = startWeek; week <= endWeek; week++) {
            if (type == 1 && week % 2 != 0) {
                weeks.add(week);
            } else if (type == 2 && week % 2 == 0) {
                weeks.add(week);
            } else if (type == 3) {
                weeks.add(week);
            }
        }

        return weeks;
    }
}
