package com.dingtalk.controller;

import com.dingtalk.config.AppConfig;
import com.dingtalk.model.RpcServiceResult;
import com.dingtalk.service.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业内部用户（免）登录
 */
@RestController
@Slf4j
public class LoginController {

    @Resource
    private UserManager userManager;

    /**
     * 欢迎页面, 检查后端服务是否启动
     *
     * @return
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/confirm")
    public String confirm(){
        return "已收到";
    }

    /**
     * 根据免登授权码, 获取登录用户身份
     *
     * @param authCode 免登授权码
     * @return
     */
    @GetMapping(value = "/login")
    public RpcServiceResult login(@RequestParam(value = "authCode") String authCode) {
        log.info("login request!!! authCode:{}", authCode);
        try {
            // 1. 获取用户id
            String userId = userManager.getUserId(authCode);
            // 2. 获取用户名称
            String userName = userManager.getUserName(userId);
            // 3. 返回用户身份
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("userId", userId);
            resultMap.put("userName", userName);

            return RpcServiceResult.getSuccessResult(resultMap);
        } catch (Exception ex) {
            ex.printStackTrace();
            return RpcServiceResult.getFailureResult("-1", "login exception");
        }
    }

    /**
     * 获取corpId
     * @return
     */
    @RequestMapping(value = "/getCorpId", method = RequestMethod.GET)
    public String getCorpId() {
        log.info("coroId:{}", AppConfig.getCorpId());
        return AppConfig.getCorpId();
    }
}
