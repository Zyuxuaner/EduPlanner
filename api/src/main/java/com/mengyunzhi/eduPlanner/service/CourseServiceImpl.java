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

/**
 * @author zhangyuxuan wangyuhanhhh
 */
@Service
public class CourseServiceImpl implements CourseService{
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

    private final static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Override
    public Response<String> delete(Long courseInfoId) {
        Optional<CourseInfo> courseInfoOptional = this.courseInfoRepository.findById(courseInfoId);
        if (!courseInfoOptional.isPresent()) {
            return Response.fail("课程安排不存在");
        }
        CourseInfo courseInfo = courseInfoOptional.get();

        // 判断该课程安排是否有学生复用
        if (!courseInfo.getStudents().isEmpty()) {
            return Response.fail("该课程已被学生复用，无法删除");
        }

        courseInfoRepository.delete(courseInfo);
        return Response.success(null, "删除成功");
    }

    @Override
    public Response<String> save(CourseDto.SaveRequest saveRequest, Long userId, Long schoolId) {
        Long ACTIVE_STATUS = 1L;
        Term activeTerm = termRepository.findBySchoolIdAndStatus(schoolId, ACTIVE_STATUS)
                .orElseThrow(() -> new IllegalArgumentException("该学校下没有激活学期"));

        Student student = studentRepository.findByUserId(userId);
        if (student == null) {
            throw new IllegalArgumentException("学生信息不存在");
        }

        Course existingCourse = courseRepository.findByNameAndTermId(saveRequest.getName(), activeTerm.getId());
        CourseInfo newCourseInfo = new CourseInfo();

        if (existingCourse != null) {
            // 如果课程已存在，使用现有的课程
            newCourseInfo.setCourse(existingCourse);
        } else {
            // 如果课程不存在，创建新课程
            Course newCourse = new Course();
            newCourse.setName(saveRequest.getName());
            newCourse.setTerm(activeTerm);

            courseRepository.save(newCourse);
            newCourseInfo.setCourse(newCourse);
        }

        newCourseInfo.setWeekType(saveRequest.getWeekType());
        newCourseInfo.setDay(saveRequest.getDay());
        newCourseInfo.setBegin(saveRequest.getBegin());
        newCourseInfo.setLength(saveRequest.getLength());
        newCourseInfo.setWeeks(saveRequest.getWeeks());
        newCourseInfo.setCreator(student);

        courseInfoRepository.save(newCourseInfo);
        return Response.success(null, "课程新增成功");

    }

    /**
     * 转换成 GetAllCoursesResponse 类型，并填充课程安排信息
     * @return courseResponses
     */
    @Override
    public List<CourseDto.GetAllCoursesResponse> getAllCourses() {
        List<CourseInfo> courseInfoList = courseInfoRepository.findAll();
        List<CourseDto.GetAllCoursesResponse> coursesResponses = new ArrayList<>();

        for (CourseInfo courseInfo : courseInfoList) {
            CourseDto.GetAllCoursesResponse response = new CourseDto.GetAllCoursesResponse();

            response.setName(courseInfo.getCourse().getName());
            response.setCourseInfo(courseInfo);
            response.setTerm(courseInfo.getCourse().getTerm());
            response.setCreator(courseInfo.getCreator());
            // 设置复用该课程的学生
            response.setReuseStudents(new ArrayList<>(courseInfo.getStudents()));

            coursesResponses.add(response);
        }
        return coursesResponses;
    }

    @Override
    public Response<String> reuseCourseInfo(Long courseInfoId, Long studentId) {

        Optional<CourseInfo> courseInfoOptional = courseInfoRepository.findById(courseInfoId);
        if (!courseInfoOptional.isPresent()) {
            return Response.fail("该课程不存在");
        }
        CourseInfo courseInfo = courseInfoOptional.get();

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            return Response.fail("该学生不存在");
        }
        Student student = studentOptional.get();

        courseInfo.getStudents().add(student);
        courseInfoRepository.save(courseInfo);

        return Response.success(null,"复用成功");

    }

    @Override
    public Response<String> cancelReuseCourseInfo(Long courseInfoId, Long studentId) {
        Optional<CourseInfo> courseInfoOptional = courseInfoRepository.findById(courseInfoId);
        if (!courseInfoOptional.isPresent()) {
            return Response.fail("该课程不存在");
        }
        CourseInfo courseInfo = courseInfoOptional.get();

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            return Response.fail("该学生不存在");
        }
        Student student = studentOptional.get();

        courseInfo.getStudents().remove(student);
        courseInfoRepository.save(courseInfo);

        return Response.success(null,"取消复用成功");
    }

    @Override
    public Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> getMessage(Long schoolId, Long week, Long studentId) {
        // 用来存储返回的数据结构
        Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> studentCourseData = new HashMap<>();

        // 获取激活状态的学期 ID
        Long ACTIVE_STATUS = 1L;
        Optional<Term> term = this.termRepository.findBySchoolIdAndStatus(schoolId, ACTIVE_STATUS);
        Long termId = term.get().getId();

        List<Student> students;
        if (studentId != null) {
            Optional<Student> data = studentRepository.findById(studentId);
            students = Collections.singletonList(data.get());
        } else {
            // 查询该学校的所有学生
            students = studentRepository.findBySchoolId(schoolId);
        }


        // 遍历每个学生，获取他们的课程安排
        for (Student student : students) {
            Long studentsId = student.getId();
            // 获取该学生的课程信息
            List<CourseInfo> courseInfos = courseInfoRepository.findByCreator(student);
            List<CourseInfo> reusedCourseInfos = courseInfoRepository.findByStudentsId(studentsId);

            List<CourseInfo> allCourseInfos = new ArrayList<>(courseInfos);
            allCourseInfos.addAll(reusedCourseInfos);

            for (CourseInfo courseInfo : allCourseInfos) {
                // 检查是否属于当前学期，并且周次匹配
                if (courseInfo.getCourse().getTerm().getId().equals(termId) && courseInfo.getWeeks().contains(week.intValue())) {
                    // 提取课程安排信息
                    Long day = courseInfo.getDay();
                    Long begin = courseInfo.getBegin();
                    Long length = courseInfo.getLength();

                    // 获取当天的课程安排列表
                    List<CourseDto.StudentsCoursesOfSchoolResponse> daySchedule = studentCourseData.computeIfAbsent(day, k -> new ArrayList<>());

                    // 查找是否有已存在的相同时间段的课程安排
                    boolean found = false;
                    for (CourseDto.StudentsCoursesOfSchoolResponse response : daySchedule) {
                        if (response.getBegin().equals(begin) && response.getLength().equals(length)) {
                            // 如果存在，则将当前学生添加到 students 列表中
                            response.getStudents().add(student.getName());
                            found = true;
                            break;
                        }
                    }

                    // 如果未找到，则创建新的课程安排对象
                    if (!found) {
                        CourseDto.StudentsCoursesOfSchoolResponse response = new CourseDto.StudentsCoursesOfSchoolResponse();
                        response.setBegin(begin);
                        response.setLength(length);
                        response.setStudents(new ArrayList<>(Arrays.asList(student.getName())));
                        daySchedule.add(response);
                    }
                }
            }
        }

        return studentCourseData;
    }
//    @Override
//    public boolean isTimeLegal(CourseDto.SaveRequest saveRequest) {
//        Response<CurrentUser> currentUser = this.loginService.getCurrentLoginUser();
//
//        Long termId = this.getTermIdByLoginUser(currentUser);
//        Long clazzId = this.getClassIdByLoginUser(currentUser);
//
//        // courseRequest对应的courseInfo
//        CourseInfo courseInfoRequest = saveCourseInfo(saveRequest);
//
//        // 查询所有相关课程
//        List<Course> courses = this.courseRepository.findByTermIdAndClazzId(termId, clazzId);
//
//        for (Course existingCourse : courses) {
//            // 获取已经存在的course对应的courseInfos
//            Long courseId = existingCourse.getId();
//            List<CourseInfo> courseInfos = this.courseInfoRepository.findAllByCourseId(courseId);
//            for (CourseInfo existingCourseInfo : courseInfos) {
//                if (isTimeConflict(courseInfoRequest, existingCourseInfo)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public boolean isTimeConflict(CourseInfo newCourseInfo, CourseInfo existingCourseInfo) {
//        Set<Long> newWeeks = getWeeksInRange(newCourseInfo.getStartWeek(), newCourseInfo.getEndWeek(), newCourseInfo.getType());
//        Set<Long> existingWeeks = getWeeksInRange(existingCourseInfo.getStartWeek(), existingCourseInfo.getEndWeek(), newCourseInfo.getType());
//
//        if (newCourseInfo.getDay().equals(existingCourseInfo.getDay())) {
//            // 合法情况：新课程的开始时间大于旧课程的结束时间 && 新课程的结束时间小于旧课程的开时间
//            if (newCourseInfo.getBegin() < existingCourseInfo.getBegin() + existingCourseInfo.getLength() &&
//                    newCourseInfo.getBegin() + newCourseInfo.getLength() > existingCourseInfo.getBegin()) {
//                // 时间不合法，再检查周数是否重叠
//                for (Long newWeek : newWeeks) {
//                    if (existingWeeks.contains(newWeek)) {
//                        // 如果周数有重叠，有时间冲突，返回true
//                        return true;
//                    }
//                }
//            }
//        }
//
//        return false;
//    }
//
//    @Override
//    public Set<Long> getWeeksInRange(Long startWeek, Long endWeek, Long type) {
//        Set<Long> weeks = new HashSet<>();
//
//        for (long week = startWeek; week <= endWeek; week++) {
//            if (type == 1 && week % 2 != 0) {
//                weeks.add(week);
//            } else if (type == 2 && week % 2 == 0) {
//                weeks.add(week);
//            } else if (type == 3) {
//                weeks.add(week);
//            }
//        }
//
//        return weeks;
//    }
}
