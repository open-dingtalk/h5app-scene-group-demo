package com.dingtalk.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkim_1_0.Client;
import com.aliyun.dingtalkim_1_0.models.*;
import com.aliyun.tea.TeaConverter;
import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaPair;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiImChatScenegroupCreateRequest;
import com.dingtalk.api.response.OapiImChatScenegroupCreateResponse;
import com.dingtalk.config.TemplateConfig;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.util.AccessTokenUtil;
import com.dingtalk.util.RandomUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

/**
 * @author nannanness
 */
@Service
@Slf4j
public class SceneGroupManager {

    /**
     * 创建场景群
     *
     * @param
     * @return
     */
    public String createSceneGroup(String userId, String title) throws ApiException {
        // 1. 获取access_token
        String accessToken = AccessTokenUtil.getAppAccessToken();

        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.SCENE_GROUP_CREATE);
        OapiImChatScenegroupCreateRequest req = new OapiImChatScenegroupCreateRequest();
        req.setOwnerUserId(userId);
        req.setTitle(title);
        req.setTemplateId(TemplateConfig.getGroupTemplateId());
        OapiImChatScenegroupCreateResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        if(rsp.getSuccess()){
            return rsp.getResult().getOpenConversationId();
        }
        return null;
    }

    /**
     * 发送动态卡片
     *
     * @param openConversationId
     * @param cardParamMap
     * @return
     * @throws Exception
     */
    public String sendCardMsg(String openConversationId, Map<String, String> cardParamMap) throws Exception {
        Client client = createClient();
        String accessToken = AccessTokenUtil.getAppAccessToken();
        String randomString = RandomUtil.getRandomString(10);
        SendInteractiveCardHeaders sendInteractiveCardHeaders = new SendInteractiveCardHeaders();
        sendInteractiveCardHeaders.xAcsDingtalkAccessToken = accessToken;
        SendInteractiveCardRequest.SendInteractiveCardRequestCardData cardData = new SendInteractiveCardRequest.SendInteractiveCardRequestCardData()
                .setCardParamMap(cardParamMap);
        SendInteractiveCardRequest request = new SendInteractiveCardRequest()
                .setCardTemplateId(TemplateConfig.getCardTemplateId())
                .setOpenConversationId(openConversationId)
                .setOutTrackId(randomString)
                .setRobotCode(TemplateConfig.getBotTemplateId())
                .setConversationType(1)
                .setCardData(cardData);
        SendInteractiveCardResponse rsp = client.sendInteractiveCardWithOptions(request, sendInteractiveCardHeaders, new RuntimeOptions());
        System.out.println("sendCardMsg rsp : " + JSON.toJSONString(rsp));
        if(rsp.body.success){
            return randomString;
        }
        return null;
    }

    /**
     * 创建吊顶卡片
     */
    public String createTopCard(String openConversationId, Map<String, String> cardParamMap) throws Exception {
        // 1. 获取access_token
        String accessToken = AccessTokenUtil.getAppAccessToken();
        String randomString = RandomUtil.getRandomString(10);
        Client client = createClient();
        InteractiveCardCreateInstanceHeaders interactiveCardCreateInstanceHeaders = new InteractiveCardCreateInstanceHeaders();
        interactiveCardCreateInstanceHeaders.xAcsDingtalkAccessToken = accessToken;
        InteractiveCardCreateInstanceRequest.InteractiveCardCreateInstanceRequestCardData cardData = new InteractiveCardCreateInstanceRequest.InteractiveCardCreateInstanceRequestCardData()
                .setCardParamMap(cardParamMap);
        InteractiveCardCreateInstanceRequest interactiveCardCreateInstanceRequest = new InteractiveCardCreateInstanceRequest()
                .setCardTemplateId(TemplateConfig.getCardTemplateId())
                .setOpenConversationId(openConversationId)
                .setOutTrackId(randomString)
                .setRobotCode(TemplateConfig.getBotTemplateId())
                // 设置为群消息
                .setConversationType(1)
                .setCardData(cardData);
        try {
            InteractiveCardCreateInstanceResponse rsp = client.interactiveCardCreateInstanceWithOptions(interactiveCardCreateInstanceRequest, interactiveCardCreateInstanceHeaders, new RuntimeOptions());
            System.out.println("createTopCard rsp: " + JSON.toJSONString(rsp));
        } catch (TeaException err) {
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                log.error("createTopCard err!!! code:{} \n msg:{} ", err.code, err.message);
            }
        }
        return randomString;
    }

    /**
     * 开启吊顶卡片
     *
     * @param openConversationId
     * @param outTrackId
     * @return
     * @throws Exception
     */
    public TopboxOpenResponse openTopCard(String openConversationId, String outTrackId) throws Exception {
        String accessToken = AccessTokenUtil.getAppAccessToken();
        Client client = createClient();
        TopboxOpenHeaders topboxOpenHeaders = new TopboxOpenHeaders();
        topboxOpenHeaders.xAcsDingtalkAccessToken = accessToken;
        TopboxOpenRequest topboxOpenRequest = new TopboxOpenRequest()
                .setOpenConversationId(openConversationId)
                .setOutTrackId(outTrackId);
        TopboxOpenResponse topboxOpenResponse = null;
        try {
            topboxOpenResponse = client.topboxOpenWithOptions(topboxOpenRequest, topboxOpenHeaders, new RuntimeOptions());
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("createTopCard err!!! code:{} \n msg:{} ", err.code, err.message);
            }
        }
        return topboxOpenResponse;
    }

    /**
     * 关闭吊顶卡片
     *
     * @param openConversationId
     * @param outTrackId
     * @return
     * @throws Exception
     */
    public TopboxCloseResponse closeTopCard(String openConversationId, String outTrackId) throws Exception {
        String accessToken = AccessTokenUtil.getAppAccessToken();
        Client client = createClient();
        TopboxCloseHeaders topboxCloseHeaders = new TopboxCloseHeaders();
        topboxCloseHeaders.xAcsDingtalkAccessToken = accessToken;
        TopboxCloseRequest topboxCloseRequest = new TopboxCloseRequest()
                .setOpenConversationId(openConversationId)
                .setOutTrackId(accessToken);
        TopboxCloseResponse topboxCloseResponse = null;
        try {
            topboxCloseResponse = client.topboxCloseWithOptions(topboxCloseRequest, topboxCloseHeaders, new RuntimeOptions());
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                log.error("createTopCard err!!! code:{} \n msg:{} ", err.code, err.message);
            }
        }
        return topboxCloseResponse;
    }

    /**
     * 更新卡片消息s
     *
     * @param outTrackId
     * @param cardParamMap
     * @return
     * @throws Exception
     */
    public UpdateInteractiveCardResponse updateMsg(String outTrackId, Map<String, String> cardParamMap) throws Exception {
        String accessToken = AccessTokenUtil.getAppAccessToken();
        Client client = createClient();
        UpdateInteractiveCardHeaders updateInteractiveCardHeaders = new UpdateInteractiveCardHeaders();
        updateInteractiveCardHeaders.xAcsDingtalkAccessToken = accessToken;
        UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardData cardData = new UpdateInteractiveCardRequest.UpdateInteractiveCardRequestCardData()
                .setCardParamMap(cardParamMap);
        UpdateInteractiveCardRequest updateInteractiveCardRequest = new UpdateInteractiveCardRequest()
                .setOutTrackId(outTrackId)
                .setCardData(cardData)
                .setUserIdType(1);
        UpdateInteractiveCardResponse rsp = client.updateInteractiveCardWithOptions(updateInteractiveCardRequest, updateInteractiveCardHeaders, new RuntimeOptions());
        log.info("update msg rsp:{}", rsp);
        return rsp;
    }

    public Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new Client(config);
    }
}
