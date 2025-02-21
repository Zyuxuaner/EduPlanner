package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.config.DingTalkConfig;
import com.mengyunzhi.eduPlanner.dto.DingTalkResponse;
import com.mengyunzhi.eduPlanner.entity.CourseInfo;
import com.mengyunzhi.eduPlanner.entity.Student;
import com.mengyunzhi.eduPlanner.entity.Term;
import com.mengyunzhi.eduPlanner.repository.CourseInfoRepository;
import com.mengyunzhi.eduPlanner.repository.StudentRepository;
import com.mengyunzhi.eduPlanner.repository.TermRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DingTalkServiceImpl implements DingTalkService {

    private static final Logger logger = LoggerFactory.getLogger(DingTalkServiceImpl.class);

    private final TermRepository termRepository;
    private final CourseInfoRepository courseInfoRepository;
    private final StudentRepository studentRepository;
    private final DingTalkConfig dingTalkConfig;

    @Autowired
    public DingTalkServiceImpl(CourseInfoRepository courseInfoRepository,
                               StudentRepository studentRepository,
                               DingTalkConfig dingTalkConfig,
                               TermRepository termRepository) {
        this.courseInfoRepository = courseInfoRepository;
        this.studentRepository = studentRepository;
        this.dingTalkConfig = dingTalkConfig;
        this.termRepository = termRepository;
    }

    // 根据小节号获取大节名称
    public String getMajorSectionName(Long sectionNumber) {
        // 处理无效情况
        if (sectionNumber == null || sectionNumber < 1 || sectionNumber > 11) {
            return null;
        }
        if (sectionNumber >= 1 && sectionNumber <= 2) {
            return "一";
        } else if (sectionNumber >= 3 && sectionNumber <= 4) {
            return "二";
        } else if (sectionNumber >= 5 && sectionNumber <= 6) {
            return "三";
        } else if (sectionNumber >= 7 && sectionNumber <= 8) {
            return "四";
        } else if (sectionNumber >= 9 && sectionNumber <= 11) {
            return "五";
        }
        return null;
    }

    // 获取当前是第几周
    public int getCurrentWeek(Long termId) {
        Optional<Term> termOptional = termRepository.findById(termId);
        if (!termOptional.isPresent()) {
            throw new IllegalArgumentException("未找到id为：" + termId + "的学期");
        }

        Timestamp startTime = termOptional.get().getStartTime();
        LocalDate today = LocalDate.now();
        LocalDate termStartDate = startTime.toLocalDateTime().toLocalDate();

        return (int) ChronoUnit.WEEKS.between(termStartDate, today) + 1;
    }

    public static ZonedDateTime[] getTomorrowTimeRange() {
        // 获取今天的午夜时间
        LocalDateTime todayMidnight = LocalDateTime.now().toLocalDate().atStartOfDay();
        // 计算明天午夜的时间
        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
        // 明天的开始时间
        ZonedDateTime startOfTomorrow = tomorrowMidnight.atZone(ZoneId.systemDefault());
        // 明天的结束时间
        ZonedDateTime endOfTomorrow = startOfTomorrow.plusDays(1);

        return new ZonedDateTime[]{startOfTomorrow, endOfTomorrow};
    }

    @Override
    public List<DingTalkResponse> allSchedule() {
        ZonedDateTime[] timeRange = getTomorrowTimeRange();
        Date startTime = Date.from(timeRange[0].toInstant());
        Date endTime = Date.from(timeRange[1].toInstant());

        // 获取明天是星期几
        int day = timeRange[0].getDayOfWeek().getValue();
        // 检查当前时间段是否有激活的学期
        List<Term> activeTerms = termRepository.findByStatusAndStartTimeAndEndTime(1L, startTime, endTime);
        if (activeTerms.isEmpty()) {
            // 返回空列表，明天没有课程
            return Collections.emptyList();
        }

        for (Term term : activeTerms) {
            // 获取明天对应的是第几周
            Long termId = term.getId();
            int currentWeek = getCurrentWeek(termId);

            // 获取激活的学期下的school id
            Long schoolId = term.getSchool().getId();
            // 获取该学校对应的学生
            List<Student> students = studentRepository.findBySchoolId(schoolId);
            // 初始化所有学生的课表
            List<DingTalkResponse> allSchedules = new ArrayList<>();
            if (!students.isEmpty()) {
                for (Student student : students) {
                    Long studentId = student.getId();
                    String studentName = student.getName();
                    // 查询出该学生自己创建的课程
                    List<CourseInfo> myCourseInfos = courseInfoRepository.findByDayAndCreator((long) day, student);

                    // 查询出该学生复用的别人的课程
                    List<CourseInfo> reusedCourseInfos = courseInfoRepository.findByStudentsId(studentId);

                    List<CourseInfo> filteredCourseInfos = reusedCourseInfos.stream()
                            .filter(courseInfo -> courseInfo.getDay() == (long) day)
                            .collect(Collectors.toList());
                    List<CourseInfo> allCourseInfos = new ArrayList<>(myCourseInfos);
                    allCourseInfos.addAll(reusedCourseInfos);

                    // 初始化五大节状态，默认无课
                    Map<String, String> sections = new LinkedHashMap<String, String>() {{
                        put("一", "- -");
                        put("二", "- -");
                        put("三", "- -");
                        put("四", "- -");
                        put("五", "- -");
                    }};
                    for (CourseInfo courseInfo : allCourseInfos) {
                        if (courseInfo.getWeeks() != null && courseInfo.getWeeks().contains(currentWeek)) {
                            for (Long i = courseInfo.getBegin(); i < courseInfo.getBegin() + courseInfo.getLength() - 1; i++) {
                                String majorSection = getMajorSectionName(i);
                                if (majorSection != null) {
                                    sections.put(majorSection, "有课");
                                }
                            }
                        }
                    }
                    // 将结果封装到 DingTalkResponse 对象中
                    DingTalkResponse response = new DingTalkResponse(studentName, sections);
                    allSchedules.add(response);

                }
                return allSchedules;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public String generateTable(List<DingTalkResponse> schedules) {
        // 定义时间段
        String[] periods = {"    ", "一", "二", "三", "四", "五"};

        // 构建表头
        StringBuilder tableText = new StringBuilder();
        tableText.append("明日课表\n\n");
        // 空格对齐
        tableText.append("    ");
        for (String period : periods) {
            // 每个时间段占4个字符宽度
            tableText.append(period).append("    ");
        }
        tableText.append("\n");

        // 构建表格内容
        for (DingTalkResponse schedule : schedules) {
            String name = schedule.getName();
            Map<String, String> sections = schedule.getSections();

            // 添加姓名 姓名后面留空格
            tableText.append(name).append("    ");

            // 遍历时间段，填充对应的状态 每个状态占4个字符宽度
            for (String period : periods) {
                String status = sections.getOrDefault(period, "- -");
                tableText.append(status).append("    ");
            }
            tableText.append("\n");
        }

        return tableText.toString();
    }

    @Override
    public void sendWebhookRequest(String text) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> data = new HashMap<>();
        Map<String, String> textContent = new HashMap<>();
        textContent.put("tittle", "明日课表");
        textContent.put("content", text);
        data.put("msgtype", "text");
        data.put("text", textContent);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(data, headers);

        restTemplate.postForObject(dingTalkConfig.getWebhookUrl(), request, String.class);
    }

}
