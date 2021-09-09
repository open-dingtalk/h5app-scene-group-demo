package com.dingtalk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private static String appKey;

    private static String appSecret;

    private static String corpId;

    private static Long agentId;

    public static Long getAgentId() {
        return agentId;
    }

    @Value("${app.agent_id}")
    public void setAgentId(String agentId) {
        AppConfig.agentId = Long.parseLong(agentId);
    }

    public static String getAppKey() {
        return appKey;
    }

    @Value("${app.app_key}")
    public void setAppKey(String appKey) {
        AppConfig.appKey = appKey;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    @Value("${app.app_secret}")
    public void setAppSecret(String appSecret) {
        AppConfig.appSecret = appSecret;
    }

    public static String getCorpId() {
        return corpId;
    }

    @Value("${app.corp_id}")
    public void setCorpId(String corpId) {
        AppConfig.corpId = corpId;
    }
}