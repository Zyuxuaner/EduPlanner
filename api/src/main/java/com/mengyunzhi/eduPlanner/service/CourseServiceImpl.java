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
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseInfoRepository courseInfoRepository;

    @Autowired
    private LoginService loginService;

    private final static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
//    private CourseInfo saveCourseInfo(CourseDto.SaveRequest saveRequest) {
//        CourseInfo courseInfo = new CourseInfo();
//        courseInfo.setStartWeek(saveRequest.getStartWeek());
//        courseInfo.setEndWeek(saveRequest.getEndWeek());
//        courseInfo.setType(saveRequest.getStatus());
//        courseInfo.setDay(saveRequest.getWeek());
//        courseInfo.setBegin(saveRequest.getBegin());
//        courseInfo.setLength(saveRequest.getEnd() - saveRequest.getBegin() + 1);
//        return courseInfo;
//    }


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

//    @Override
//    public Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> getAllStudentsCoursesOfSchool(Long schoolId, Long week) {
//        List<Clazz> clazzList = clazzRepository.findClazzBySchoolId(schoolId);
//
//        // 用来存储返回的数据结构
//        Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> studentCourseData = new HashMap<>();
//        Long ACTIVE_STATUS = 1L;
//        // 根据 schoolId 来获取 激活学期的id
//        Optional<Term> term = this.termRepository.findBySchoolIdAndStatus(schoolId, ACTIVE_STATUS);
//        Long termId = term.get().getId();
//
//        for (Clazz clazz : clazzList) {
//            // 获取该班级的所有必修课
//            List<Course> requiredCourses = courseRepository.findByClazzIdAndTermIdAndType(clazz.getId(), termId, 2L);
//            // 获取该班级的所有选修课
//            List<Course> electiveCourses = courseRepository.findByStudentIdAndTermIdAndType(clazz.getId(), termId, 1L);
//
//            // 处理必修课
//            for (Course course : requiredCourses) {
//                processCourse(clazz, course, week, studentCourseData);
//            }
//            // 处理选修课
//            for (Course course : electiveCourses) {
//                processCourse(clazz, course, week, studentCourseData);
//            }
//        }
//
//        return studentCourseData;
//    }

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

    @Override
    public Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> getAllMessage(List<Long> schoolId, List<Long> weeks) {
        // 用来存储最终的合并数据结构
        Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> combinedStudentCourseData = new HashMap<>();

        for (int i = 0; i < schoolId.size(); i++) {
            Long schoolIdValue = schoolId.get(i);
            Long weekValue = weeks.get(i);

            try {
                // 调用getMessage获取单个学校的课程安排信息
                Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> schoolCourseData = getMessage(schoolIdValue, weekValue, null);

                // 合并结果.entrySet()方法返回Map中所有键值对集合
                for (Map.Entry<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>> entry : schoolCourseData.entrySet()) {
                    Long day = entry.getKey();
                    // 获取当前键值对中的值，及对应的课程安排
                    List<CourseDto.StudentsCoursesOfSchoolResponse> daySchedule = entry.getValue();

                    // day键不存在，自动为这个键创建一个新的ArrayList作为值。若存在，直接返回对应值
                    List<CourseDto.StudentsCoursesOfSchoolResponse> existingDaySchedule =
                            combinedStudentCourseData.computeIfAbsent(day, k -> new ArrayList<>());
                    existingDaySchedule.addAll(daySchedule);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        logger.info(combinedStudentCourseData.toString());
        return combinedStudentCourseData;
    }

//
//    /**
//     * 获取对应课程的课程安排
//     * 只获取符合当前 week 的课程安排
//     * 根据课程类型获取激活状态学生列表，必修课：获取该班级的所有学生；选修课：获取对应的学生
//     * @param clazz 班级
//     * @param course 课程
//     * @param week 第几周
//     * @param studentCourseData 返回的课程数据
//     */
//    private void processCourse(Clazz clazz, Course course, Long week, Map<Long, Map<Long, List<CourseDto.StudentsCoursesOfSchoolResponse>>> studentCourseData) {
//        List<CourseInfo> courseInfos = courseInfoRepository.findAllByCourseId(course.getId());
//
//        boolean isOddWeek = week % 2 != 0;
//        Long courseTypeToExclude = isOddWeek ? 2L : 1L;
//
//        for (CourseInfo courseInfo : courseInfos) {
//
//            if (courseInfo.getType().equals(courseTypeToExclude)) {
//                continue;
//            }
//            // 如果当前课程的开始和结束周包含了目标周
//            if (courseInfo.getStartWeek() <= week && courseInfo.getEndWeek() >= week) {
//                List<String> students = new ArrayList<>();
//
//                // 必修课获取学生名单
//                if (course.getType() == 2) {
//                    List<Student> clazzStudents = studentRepository.findAllByClazzIdAndStatus(clazz.getId(), 1L);
//                    for (Student student : clazzStudents) {
//                        students.add(student.getName());
//                    }
//                    // 选修课：获取指定学生
//                } else if (course.getType() == 1 && course.getStudentId() != null) {
//                    Student student = studentRepository.findByIdAndStatus(course.getStudentId(), 1L);
//                    if (student != null) {
//                        students.add(student.getName());
//                    }
//                }
//
//                // 构建课程安排数据
//                CourseDto.StudentsCoursesOfSchoolResponse response = new CourseDto.StudentsCoursesOfSchoolResponse();
//                response.setWeek(courseInfo.getDay());
//                response.setBegin(courseInfo.getBegin());
//                response.setLength(courseInfo.getLength());
//                response.setStudents(students);
//
//                // 按照课程 ID 和天（day）将课程安排按需要的结构添加到 studentCourseData
//                studentCourseData
//                        .computeIfAbsent(course.getId(), k -> new HashMap<>())
//                        .computeIfAbsent(courseInfo.getDay(), k -> new ArrayList<>())
//                        .add(response);
//            }
//        }
//    }
//
//    @Override
//    public Map<Long, Map<Long, List<CourseDto.StudentCourseInfoResponse>>> getCourseInfoByCurrentUserOfWeek(Long clazzId, Long studentId, Long week, Long termId) {
//        Map<Long, Map<Long, List<CourseDto.StudentCourseInfoResponse>>> result = new HashMap<>();
//
//        boolean isOddWeek = week % 2 != 0;
//        // 如果是奇数周，排除双周课程（type 2）；如果是偶数周，排除单周课程（type 1）
//        Long courseTypeToExclude = isOddWeek ? 2L : 1L;
//
//        List<Course> requiredCourses = courseRepository.findByClazzIdAndTermIdAndType(clazzId, termId, 2L);
//        List<Course> electiveCourses = courseRepository.findByStudentIdAndTermIdAndType(studentId, termId, 1L);
//
//        List<Course> allCourses = new ArrayList<>();
//        allCourses.addAll(requiredCourses);
//        allCourses.addAll(electiveCourses);
//
//        for (Course course : allCourses) {
//            List<CourseInfo> courseInfos = courseInfoRepository.findAllByCourseId(course.getId());
//
//            for (CourseInfo courseInfo : courseInfos) {
//                if (courseInfo.getType().equals(courseTypeToExclude)) {
//                    continue;
//                }
//
//                if (week >= courseInfo.getStartWeek() && week <= courseInfo.getEndWeek()) {
//                    CourseDto.StudentCourseInfoResponse response = new CourseDto.StudentCourseInfoResponse();
//                    response.setWeek(courseInfo.getDay());
//                    response.setBegin(courseInfo.getBegin());
//                    response.setLength(courseInfo.getLength());
//                    response.setName(course.getName());
//
//                    Map<Long, List<CourseDto.StudentCourseInfoResponse>> weekMap = result.getOrDefault(week, new HashMap<>());
//                    weekMap.computeIfAbsent(course.getId(), k -> new ArrayList<>()).add(response);
//                    result.put(week, weekMap);
//                }
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public Long getTermIdByLoginUser(Response<CurrentUser> currentUser) {
//        Long schoolId = currentUser.getData().getSchoolId();
//        Optional<Term> term = this.termRepository.findBySchoolIdAndStatus(schoolId, 1L);
//        Long termId = term.get().getId();
//        return termId;
//    }
//
//    @Override
//    public Long getClassIdByLoginUser(Response<CurrentUser> currentUser) {
//        Long userId = currentUser.getData().getId();
//        Student student = this.studentRepository.findByUserId(userId);
//        return student.getClazz().getId();
//    }
//
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
