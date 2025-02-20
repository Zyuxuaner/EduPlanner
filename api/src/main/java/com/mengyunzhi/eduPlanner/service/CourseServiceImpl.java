package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.CourseDto;
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
    private TermService termService;

    private final static Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    /**
     * 检查课程安排是否合理
     * 1、获取该学期下所有课程 courseList
     * 2、获取本学生创建的课程安排 courseInfoList
     * 3、获取该学生复用的所有课程安排 reuseCourseInfos
     * 4、调用 isConflict 方法判断是否存在冲突
     * @param student 操作的学生
     * @param activeTerm 该学生所在学校的激活学期
     * @param newCourseInfo 需要判断的课程安排
     * @return true：有冲突；false：没有冲突
     */
    private boolean check(Student student, Term activeTerm, CourseInfo newCourseInfo) {
        List<CourseInfo> existingCourseInfoList = new ArrayList<>();
        List<Course> courseList = this.courseRepository.findAllByTermId(activeTerm.getId());

        for (Course course : courseList) {
            // 该学生为创建者的在 day 下的所有课程安排
            List<CourseInfo> courseInfoList = this.courseInfoRepository.findAllByCourseIdAndDayAndCreatorId(course.getId(), newCourseInfo.getDay(), student.getId());
            existingCourseInfoList.addAll(courseInfoList);
        }

        // 找到该学生复用的课程
        List<CourseInfo> reuseCourseInfos = this.courseInfoRepository.findAllByStudentsId(student.getId());
        existingCourseInfoList.addAll(reuseCourseInfos);

        for (CourseInfo existingCourseInfo : existingCourseInfoList) {
            return isConflict(existingCourseInfo, newCourseInfo);
        }

        return false;
    }

    /**
     * 判断两个课程安排是否有时间冲突
     * 1、首先判断周次是否有交集
     * 2、判断上课星期数是否相同（实际上是一定相同的，因为查询的时候是使用了 newCourseInfo 的 day 为查询条件进行查询的）
     * 3、判断节次是否有交集
     * @param existing 已持久化课程安排
     * @param newCourseInfo 待持久化课程安排
     * @return true：有冲突；false：没有冲突
     */
    private boolean isConflict(CourseInfo existing, CourseInfo newCourseInfo) {
        for (Integer week : newCourseInfo.getWeeks()) {
            if (existing.getWeeks().contains(week)) {
                if (existing.getDay().equals(newCourseInfo.getDay())) {
                    boolean result = (newCourseInfo.getBegin() < existing.getBegin() + existing.getLength() &&
                            newCourseInfo.getBegin() + newCourseInfo.getLength() > existing.getBegin() ||
                            (existing.getBegin() < newCourseInfo.getBegin() + newCourseInfo.getLength() &&
                            existing.getBegin() + existing.getLength() > newCourseInfo.getBegin()));
                    if (result) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

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
        // 标记是否新创建课程
        boolean isNewCourseCreated = false;

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
            // 标记新课程已创建
            isNewCourseCreated = true;
        }

        newCourseInfo.setWeekType(saveRequest.getWeekType());
        newCourseInfo.setDay(saveRequest.getDay());
        newCourseInfo.setBegin(saveRequest.getBegin());
        newCourseInfo.setLength(saveRequest.getLength());
        newCourseInfo.setWeeks(saveRequest.getWeeks());
        newCourseInfo.setCreator(student);

        // 数据验证
        boolean result = check(student, activeTerm, newCourseInfo);

        if (result) {
            // 如果发生冲突，删除新创建的课程
            if (isNewCourseCreated) {
                courseRepository.delete(newCourseInfo.getCourse());
            }
            return Response.fail("课程时间发生冲突，新增失败");
        }

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
        CourseInfo reuseCourseInfo = courseInfoOptional.get();

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            return Response.fail("该学生不存在");
        }
        Student student = studentOptional.get();

        Long ACTIVE_STATUS = 1L;
        Optional<Term> termOptional = termRepository.findBySchoolIdAndStatus(student.getSchool().getId(), ACTIVE_STATUS);
        if (!termOptional.isPresent()) {
            return Response.fail("请先激活学期");
        }
        Term activeTerm = termOptional.get();

        // 对复用课程进行验证
        boolean result = check(student, activeTerm, reuseCourseInfo);
        if (result) {
            return Response.fail("课程时间发生冲突，复用失败");
        }

        reuseCourseInfo.getStudents().add(student);
        courseInfoRepository.save(reuseCourseInfo);

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

    @Override
    public Response<CourseDto.GetCourseInfoByIdResponse> getCourseInfoById(Long courseInfoId) {
        Optional<CourseInfo> courseInfoOptional = this.courseInfoRepository.findById(courseInfoId);

        if (!courseInfoOptional.isPresent()) {
            return Response.fail("该课程安排不存在");
        }
        CourseInfo courseInfo = courseInfoOptional.get();

        CourseDto.GetCourseInfoByIdResponse response = new CourseDto.GetCourseInfoByIdResponse();
        Long totalWeeks = this.termService.getTotalWeeksByTerm(courseInfo.getCourse().getTerm());

        response.setName(courseInfo.getCourse().getName());
        response.setTerm(courseInfo.getCourse().getTerm());
        response.setTotalWeeks(totalWeeks);
        response.setCourseInfo(courseInfo);

        return Response.success(response, "获取成功");
    }

}
