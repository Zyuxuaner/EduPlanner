package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.DingTalkRequest;
import com.mengyunzhi.eduPlanner.entity.CourseInfo;
import com.mengyunzhi.eduPlanner.repository.CourseInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DingTalkServiceImpl implements DingTalkService {

    private CourseInfoRepository courseInfoRepository;
    @Autowired
    public DingTalkServiceImpl(CourseInfoRepository courseInfoRepository) {
        this.courseInfoRepository = courseInfoRepository;
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
    public List<DingTalkRequest> allSchedule() {
        return null;
    }

    @Override
    public Map<String, Map<Long, String>> oneSchedule(Long studentId) {
        ZonedDateTime[] timeRange = getTomorrowTimeRange();

        Date startTime = Date.from(timeRange[0].toInstant());
        Date endTime = Date.from(timeRange[1].toInstant());

        // 获取明天是星期几
        int day = timeRange[0].getDayOfWeek().getValue();

        // 查询明天一天内学生的课程安排
        List<CourseInfo> courseInfos = this.courseInfoRepository.findCoursesByDateRangeAndStudent(startTime, endTime, (long)day, 1L, 11L, studentId);
    }
}
