package com.dingtalk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemplateConfig {

    private static String groupTemplateId;

    private static String botTemplateId;

    private static String cardTemplateId;

    public static String getGroupTemplateId() {
        return groupTemplateId;
    }

    @Value("${groupTemplateId}")
    public void setGroupTemplateId(String groupTemplateId) {
        TemplateConfig.groupTemplateId = groupTemplateId;
    }

    public static String getBotTemplateId() {
        return botTemplateId;
    }

    @Value("${botTemplateId}")
    public void setBotTemplateId(String botTemplateId) {
        TemplateConfig.botTemplateId = botTemplateId;
    }

    public static String getCardTemplateId() {
        return cardTemplateId;
    }

    @Value("${cardTemplateId}")
    public void setCardTemplateId(String cardTemplateId) {
        TemplateConfig.cardTemplateId = cardTemplateId;
    }
}
