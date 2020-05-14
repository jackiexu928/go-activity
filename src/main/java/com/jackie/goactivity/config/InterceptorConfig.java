package com.jackie.goactivity.config;

import com.alibaba.fastjson.JSON;
import com.jackie.goactivity.common.constant.RedisConstants;
import com.jackie.goactivity.common.enums.GoActivityCodeEnum;
import com.jackie.goactivity.domain.resopnse.AccountLoginRespDTO;
import com.jackie.goactivity.entity.UserInfo;
import com.jackie.goactivity.util.TrackHolder;
import com.jackie.goactivity.util.Tracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import util.ResultMessageBuilder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-04-29
 */
public class InterceptorConfig implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(InterceptorConfig.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取token
        String token = request.getHeader("token");
        //String accountId = getCookie(request, "accountId");
        if (StringUtils.isEmpty(token)){
            log.error("获取token为空");
            printErrorMsg(response, GoActivityCodeEnum.USER_NOT_LOGIN);
            return false;
        }
        Object openIdObj = redisTemplate.opsForValue().get(RedisConstants.GO_ACTIVITY_USER_PREFIX + token);
        if (openIdObj == null){
            log.error("从redis获取openId为空");
            printErrorMsg(response, GoActivityCodeEnum.USER_NOT_LOGIN);
            return false;
        }
        String openId = openIdObj.toString();
        Object userInfo = redisTemplate.opsForValue().get(RedisConstants.GO_ACTIVITY_USER_USER_INFO + openId);
        //String userInfoCache = redisTemplate.opsForValue().get(RedisConstants.GO_ACTIVITY_USER_USER_INFO + openId).toString();
        if (userInfo == null){
            log.error("从redis获取用户信息为空");
            printErrorMsg(response, GoActivityCodeEnum.USER_NOT_LOGIN);
            return false;
        }
        AccountLoginRespDTO accountLoginRespDTO = JSON.parseObject(userInfo.toString(), AccountLoginRespDTO.class);
        Tracker tracker = new Tracker(openId, accountLoginRespDTO);
        TrackHolder.set(tracker);
        return true;
    }

    public static String getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        log.warn("getCookie cookies[] is " + JSON.toJSONString(cookies));
        String cookieValue = null;
        if (cookies != null) {
            for(int i = 0; i < cookies.length; ++i) {
                if (cookieName.equals(cookies[i].getName())) {
                    cookieValue = cookies[i].getValue();
                    break;
                }
            }
        }

        return cookieValue;
    }
    /**
     * 打印错误信息
     *
     * @param httpServletResponse
     */
    private void printErrorMsg(HttpServletResponse httpServletResponse, GoActivityCodeEnum goActivityCodeEnum) {
        OutputStream output = null;
        try {
            output = httpServletResponse.getOutputStream();
            String errorMsg = ResultMessageBuilder.build(false, goActivityCodeEnum.getErrCode(), goActivityCodeEnum.getErrMsg()).toJSONString();
            output.write(errorMsg.getBytes("utf-8"));
            output.flush();
            output.close();
        } catch (IOException e) {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e1) {

                }
            }
        }
    }
}
