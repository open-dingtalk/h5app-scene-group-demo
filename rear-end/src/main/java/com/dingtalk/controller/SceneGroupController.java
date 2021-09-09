package com.dingtalk.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.dingtalkim_1_0.models.TopboxCloseResponse;
import com.aliyun.dingtalkim_1_0.models.TopboxOpenResponse;
import com.aliyun.dingtalkim_1_0.models.UpdateInteractiveCardResponse;
import com.dingtalk.model.RpcServiceResult;
import com.dingtalk.service.SceneGroupManager;
import com.dingtalk.util.TimeUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nannanness
 */
@Slf4j
@RestController
@RequestMapping("/sceneGroup")
public class SceneGroupController {

    @Autowired
    SceneGroupManager sceneGroupManager;

    @RequestMapping("/create")
    @PostMapping
    public RpcServiceResult createGroup(@RequestParam String userId,@RequestParam String title) throws ApiException {
        String groupId = sceneGroupManager.createSceneGroup(userId, title);
        System.out.println("groupId: " + groupId);
        return RpcServiceResult.getSuccessResult(groupId);
    }

    @RequestMapping("/sendIm")
    @PostMapping
    public RpcServiceResult sendIm(@RequestParam String groupId, @RequestParam String msg) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("msg", msg);
        map.put("content", "故障信息：");
        map.put("date", TimeUtil.dateToStr(new Date()));
        String cardId = sceneGroupManager.sendCardMsg(groupId, map);
        log.info("sendIm cardId: {}", cardId);
        return RpcServiceResult.getSuccessResult(cardId);
    }

    @RequestMapping("/sendMsg")
    @PostMapping
    public RpcServiceResult sendMsg(@RequestParam String groupId, @RequestParam String msg) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("msg", msg);
        map.put("content", "故障信息：");
        map.put("date", TimeUtil.dateToStr(new Date()));
        String topCardId = sceneGroupManager.createTopCard(groupId, map);
        TopboxOpenResponse rsp = sceneGroupManager.openTopCard(groupId, topCardId);
        log.info("\n sendMsg topCardId: {} \n rsp: {}", topCardId, JSON.toJSONString(rsp));
        return RpcServiceResult.getSuccessResult(topCardId);
    }

    @RequestMapping("/updateMsg")
    @PostMapping
    public RpcServiceResult updateMsg(@RequestParam String cardId, @RequestParam String msg) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("msg", msg);
        map.put("content", "故障信息：");
        map.put("date", TimeUtil.dateToStr(new Date()));
        UpdateInteractiveCardResponse rsp = sceneGroupManager.updateMsg(cardId, map);
        log.info("\n updateMsg rsp: {}", JSON.toJSONString(rsp));
        return RpcServiceResult.getSuccessResult(rsp.body.success);
    }

    @RequestMapping("/complete")
    @PostMapping
    public RpcServiceResult complete(@RequestParam String groupId, @RequestParam String topCardId) throws Exception {
        TopboxCloseResponse rsp = sceneGroupManager.closeTopCard(groupId, topCardId);
        log.info("\ncomplete rsp: {}", JSON.toJSONString(rsp));
        return RpcServiceResult.getSuccessResult(null);
    }
}
