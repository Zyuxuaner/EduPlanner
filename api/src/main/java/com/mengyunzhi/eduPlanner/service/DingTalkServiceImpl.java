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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

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
//
//    public static ZonedDateTime[] getTomorrowTimeRange() {
//        // 获取今天的午夜时间
//        LocalDateTime todayMidnight = LocalDateTime.now().toLocalDate().atStartOfDay();
//        // 计算明天午夜的时间
//        LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
//        // 明天的开始时间
//        ZonedDateTime startOfTomorrow = tomorrowMidnight.atZone(ZoneId.systemDefault());
//        // 明天的结束时间
//        ZonedDateTime endOfTomorrow = startOfTomorrow.plusDays(1);
//
//        return new ZonedDateTime[]{startOfTomorrow, endOfTomorrow};
//    }
//
//    private boolean checkActiveTerm(Date startTime, Date endTime) {
//        // 查询数据库中的学期信息，判断是否有激活的学期
//        List<Term> activeTerms = termRepository.findByStatusAndStartTimeAndEndTime(1L,startTime, endTime);
//        return !activeTerms.isEmpty();
//    }
//
//    @Override
//    public List<DingTalkResponse> allSchedule() {
//        ZonedDateTime[] timeRange = getTomorrowTimeRange();
//
//        Date startTime = Date.from(timeRange[0].toInstant());
//        Date endTime = Date.from(timeRange[1].toInstant());
//
//        // 获取明天是星期几
//        int day = timeRange[0].getDayOfWeek().getValue();
//
//        // 检查是否有激活的学期
//        boolean isActiveTerm = checkActiveTerm(startTime, endTime);
//        if (!isActiveTerm) {
//            // 返回空列表，明天没有课程
//            return Collections.emptyList();
//        }
//
//        // 获取所有学生
//        List<Student> allStudents = studentRepository.findByStatus(1L);
//
//        // 初始化所有学生的课表
//        List<DingTalkResponse> allSchedules = new ArrayList<>();
//
//        for (Student student : allStudents) {
//            Long studentId = student.getId();
//            List<CourseInfo> courseInfos = this.courseInfoRepository.findCoursesByTomorrow(
//                    startTime,
//                    endTime,
//                    (long) day,
//                    1L,
//                    11L,
//                    studentId
//            );
//
//            // 按学生姓名分组
//            Map<Long, String> sections = new HashMap<>();
//            String studentName = null;
//            for (CourseInfo courseInfo : courseInfos) {
//                studentName = student.getName();
//
//                // 标记每个小节为有课
//                for (Long i = courseInfo.getBegin(); i < courseInfo.getBegin() + courseInfo.getLength(); i++) {
//                    sections.put(i, "有课");
//                }
//            }
//
//            // 将结果封装到 DingTalkResponse 对象中
//            DingTalkResponse response = new DingTalkResponse(studentName, sections);
//            allSchedules.add(response);
//        }
//
//        return allSchedules;
//    }
//
//    @Override
//    public String generateTable(List<DingTalkResponse> schedules) {
//        StringBuilder tableText = new StringBuilder("明日课表\n\n");
//        tableText.append("| 姓名 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 |\n");
//        tableText.append("| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |\n");
//
//        for(DingTalkResponse schedule : schedules) {
//            String studentName = schedule.getName();
//            Map<Long, String> sections = schedule.getSections();
//
//            tableText.append("| ").append(studentName).append(" | ");
//            for (Long i = 1L; i <= 11L; i++) {
//                tableText.append(sections.getOrDefault(i, ""));
//                if (i < 11L) {
//                    tableText.append(" | ");
//                }
//            }
//            tableText.append(" |\n");
//        }
//        return tableText.toString();
//    }
//
//    @Override
//    public void sendWebhookRequest(String markdownText) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, Object> data = new HashMap<>();
//        Map<String, String> markdown = new HashMap<>();
//        markdown.put("title", "明日课表");
//        markdown.put("text", markdownText);
//        data.put("msgtype", "markdown");
//        data.put("markdown", markdown);
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(data, headers);
//
//        restTemplate.postForObject(dingTalkConfig.getWebhookUrl(),request, String.class);
//    }
}
