package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.DingTalkRequest;

import java.util.List;
import java.util.Map;

public interface DingTalkService {
    /**
     * 获取明天所有人的课程
     */
    List<DingTalkRequest> allSchedule();

    /**
     * 获取一个学生的课程
     */
    Map<String, Map<Long, String>> oneSchedule(Long studentId);
}
