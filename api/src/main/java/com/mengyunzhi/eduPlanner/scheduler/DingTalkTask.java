package com.mengyunzhi.eduPlanner.scheduler;

import com.mengyunzhi.eduPlanner.dto.DingTalkResponse;
import com.mengyunzhi.eduPlanner.service.DingTalkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DingTalkTask {
    private DingTalkService dingTalkService;

    private static final Logger logger = LoggerFactory.getLogger(DingTalkTask.class);
    @Autowired
    public DingTalkTask(DingTalkService dingTalkService) {
        this.dingTalkService = dingTalkService;
    }

    // 每天晚上8点执行
    @Scheduled(cron = " 0 00 20 * * ?")
    public void sendMessage() {
        // 获取所有学生的明日课表
        List<DingTalkResponse> schedules = this.dingTalkService.allSchedule();

        if (schedules == null || schedules.isEmpty()) {
            // 没有课表，不发送信息
            logger.info("没有课程");
            return;
        }

        // 生成Markdown表格
        String text = this.dingTalkService.generateTable(schedules);

        // 发送webhook请求
        this.dingTalkService.sendWebhookRequest(text);
    }
}
