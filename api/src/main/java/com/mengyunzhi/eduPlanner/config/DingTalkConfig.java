package com.mengyunzhi.eduPlanner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DingTalkConfig {

    @Value("${dingtalk.robot.url}")
    private String webhookUrl;

    public String getWebhookUrl() {
        return webhookUrl;
    }
}
