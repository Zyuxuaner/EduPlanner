package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseDto;
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

    @Autowired
    private LoginService loginService;

    private CourseInfo saveCourseInfo(CourseDto.SaveRequest saveRequest) {
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setStartWeek(saveRequest.getStartWeek());
        courseInfo.setEndWeek(saveRequest.getEndWeek());
        courseInfo.setType(saveRequest.getStatus());
        courseInfo.setDay(saveRequest.getWeek());
        courseInfo.setBegin(saveRequest.getBegin());
        courseInfo.setLength(saveRequest.getEnd() - saveRequest.getBegin() + 1);
        return courseInfo;
    }

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
        Course isExistCourse = courseRepository.findByNameAndTypeAndTermIdAndClazzIdAndStudentId(
                saveRequest.getName(),
                saveRequest.getType(),
                activeTerm.getId(),
                clazzId,
                studentId
        );
        if (isExistCourse != null) {
            CourseInfo courseInfo = saveCourseInfo(saveRequest);
            courseInfo.setCourse(isExistCourse);
            courseInfoRepository.save(courseInfo);
            return isExistCourse;
        } else {
            Course savedCourse = courseRepository.save(course);
            CourseInfo courseInfo = saveCourseInfo(saveRequest);
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
    public List<CourseDto.GetAllCoursesForCurrentUserResponse> getAllCoursesForCurrentUser(Long clazzId, Long studentId) {
        List<Course> requiredCourses = courseRepository.findByClazzIdAndType(clazzId, 2L);
        List<Course> electiveCourses = courseRepository.findByStudentIdAndType(studentId, 1L);

        List<Course> allCourses = new ArrayList<>();
        allCourses.addAll(requiredCourses);
        allCourses.addAll(electiveCourses);

        List<CourseDto.GetAllCoursesForCurrentUserResponse> courseResponses = new ArrayList<>();
        for (Course course : allCourses) {
            // 获取该课程的所有课程安排
            List<CourseInfo> courseInfos = courseInfoRepository.findAllByCourseId(course.getId());

            // 如果该课程有课程安排
            if (courseInfos != null && !courseInfos.isEmpty()) {
                // 遍历所有课程安排
                for (CourseInfo courseInfo : courseInfos) {
                    CourseDto.GetAllCoursesForCurrentUserResponse courseResponse = new CourseDto.GetAllCoursesForCurrentUserResponse();
                    courseResponse.setName(course.getName());
                    courseResponse.setType(course.getType());

                    // 填充课程安排信息
                    courseResponse.setWeekType(courseInfo.getType());
                    courseResponse.setStartWeek(courseInfo.getStartWeek());
                    courseResponse.setEndWeek(courseInfo.getEndWeek());
                    courseResponse.setWeek(courseInfo.getDay());
                    courseResponse.setBegin(courseInfo.getBegin());
                    courseResponse.setLength(courseInfo.getLength());
                    courseResponse.setTerm(course.getTerm());

                    // 将该课程信息添加到响应列表
                    courseResponses.add(courseResponse);
                }
            }
        }

        return courseResponses;
    }

    @Override
    public Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> getAllStudentsCoursesOfSchool(Long schoolId, Long week) {
        List<Clazz> clazzList = clazzRepository.findClazzBySchoolId(schoolId);

        // 用来存储返回的数据结构
        Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> studentCourseData = new HashMap<>();
        Long ACTIVE_STATUS = 1L;
        // 根据 schoolId 来获取 激活学期的id
        Optional<Term> term = this.termRepository.findBySchoolIdAndStatus(schoolId, ACTIVE_STATUS);
        Long termId = term.get().getId();

        for (Clazz clazz : clazzList) {
            // 获取该班级的所有必修课
            List<Course> requiredCourses = courseRepository.findByClazzIdAndTermIdAndType(clazz.getId(), termId, 2L);
            // 获取该班级的所有选修课
            List<Course> electiveCourses = courseRepository.findByStudentIdAndTermIdAndType(clazz.getId(), termId, 1L);

            // 处理必修课
            for (Course course : requiredCourses) {
                processCourse(clazz, course, week, studentCourseData);
            }
            // 处理选修课
            for (Course course : electiveCourses) {
                processCourse(clazz, course, week, studentCourseData);
            }
        }

        return studentCourseData;
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

        boolean isOddWeek = week % 2 != 0;
        Long courseTypeToExclude = isOddWeek ? 2L : 1L;

        for (CourseInfo courseInfo : courseInfos) {

            if (courseInfo.getType().equals(courseTypeToExclude)) {
                continue;
            }
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

    @Override
    public Map<Long, Map<Long, List<CourseDto.StudentCourseInfoResponse>>> getCourseInfoByCurrentUserOfWeek(Long clazzId, Long studentId, Long week, Long termId) {
        Map<Long, Map<Long, List<CourseDto.StudentCourseInfoResponse>>> result = new HashMap<>();

        boolean isOddWeek = week % 2 != 0;
        // 如果是奇数周，排除双周课程（type 2）；如果是偶数周，排除单周课程（type 1）
        Long courseTypeToExclude = isOddWeek ? 2L : 1L;

        List<Course> requiredCourses = courseRepository.findByClazzIdAndTermIdAndType(clazzId, termId, 2L);
        List<Course> electiveCourses = courseRepository.findByStudentIdAndTermIdAndType(studentId, termId, 1L);

        List<Course> allCourses = new ArrayList<>();
        allCourses.addAll(requiredCourses);
        allCourses.addAll(electiveCourses);

        for (Course course : allCourses) {
            List<CourseInfo> courseInfos = courseInfoRepository.findAllByCourseId(course.getId());

            for (CourseInfo courseInfo : courseInfos) {
                if (courseInfo.getType().equals(courseTypeToExclude)) {
                    continue;
                }

                if (week >= courseInfo.getStartWeek() && week <= courseInfo.getEndWeek()) {
                    CourseDto.StudentCourseInfoResponse response = new CourseDto.StudentCourseInfoResponse();
                    response.setWeek(courseInfo.getDay());
                    response.setBegin(courseInfo.getBegin());
                    response.setLength(courseInfo.getLength());
                    response.setName(course.getName());

                    Map<Long, List<CourseDto.StudentCourseInfoResponse>> weekMap = result.getOrDefault(week, new HashMap<>());
                    weekMap.computeIfAbsent(course.getId(), k -> new ArrayList<>()).add(response);
                    result.put(week, weekMap);
                }
            }
        }
        return result;
    }

    @Override
    public Long getTermIdByLoginUser(Response<CurrentUser> currentUser) {
        Long schoolId = currentUser.getData().getSchoolId();
        Optional<Term> term = this.termRepository.findBySchoolIdAndStatus(schoolId, 1L);
        Long termId = term.get().getId();
        return termId;
    }

    @Override
    public Long getClassIdByLoginUser(Response<CurrentUser> currentUser) {
        Long userId = currentUser.getData().getId();
        Student student = this.studentRepository.findByUserId(userId);
        return student.getClazz().getId();
    }

    @Override
    public boolean isTimeLegal(CourseDto.SaveRequest saveRequest) {
        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();

        Long termId = this.getTermIdByLoginUser(currentUser);
        Long clazzId = this.getClassIdByLoginUser(currentUser);

        // courseRequest对应的courseInfo
        CourseInfo courseInfoRequest = saveCourseInfo(saveRequest);

        // 查询所有相关课程
        List<Course> courses = this.courseRepository.findByTermIdAndClazzId(termId, clazzId);

        for (Course existingCourse : courses) {
            // 获取已经存在的course对应的courseInfos
            Long courseId = existingCourse.getId();
            List<CourseInfo> courseInfos = this.courseInfoRepository.findAllByCourseId(courseId);
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
