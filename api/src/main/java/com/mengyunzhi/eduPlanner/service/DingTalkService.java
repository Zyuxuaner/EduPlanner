package com.mengyunzhi.eduPlanner.service;

import com.mengyunzhi.eduPlanner.dto.DingTalkResponse;

import java.util.List;

public interface DingTalkService {
    /**
     * 获取明天所有人的课程
     */
//    List<DingTalkResponse> allSchedule();

    /**
     * 生成Markdown表格
     * @param schedules 所有学生明日课程安排
     * @return
     */
//    String generateTable(List<DingTalkResponse> schedules);

    /**
     * 向webhookUrl发送请求
     */
//    void sendWebhookRequest(String markdownText);
}
