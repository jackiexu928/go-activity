package com.jackie.goactivity.service;

import com.alibaba.fastjson.JSON;
import com.jackie.goactivity.common.constant.RedisConstants;
import com.jackie.goactivity.dao.LoginInfoDao;
import com.jackie.goactivity.dao.UserInfoDao;
import com.jackie.goactivity.domain.request.LoginReqDTO;
import com.jackie.goactivity.domain.resopnse.LoginTokenRespDTO;
import com.jackie.goactivity.entity.LoginInfo;
import com.jackie.goactivity.entity.UserInfo;
import com.jackie.goactivity.process.AbstractService;
import com.jackie.goactivity.process.Context;
import com.jackie.goactivity.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.print.Book;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-08
 */
@Service
public class AccountService extends AbstractService {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private LoginInfoDao loginInfoDao;
    @Autowired
    private RedisTemplate redisTemplate;

    public Context<LoginReqDTO, LoginTokenRespDTO> login(LoginReqDTO reqDTO){
        Context<LoginReqDTO, LoginTokenRespDTO> context = new Context<>();
        Date now = new Date();
        //获取token
        String token;
        //避免用户短时间内频繁登陆登出操作
        //根据openId查询redis判断是否短时间内已登陆过，如果已登陆过，则返回redis中的token，否则新增
        Object cache = redisTemplate.opsForValue().get(RedisConstants.GO_ACTIVITY_USER_TOKEN + reqDTO.getOpenId());
        //String cache = redisTemplate.opsForValue().get().toString();
        if (cache == null){
            token = UuidUtil.getUUidNoSplit();
        } else {
            token = cache.toString();
        }
        //查询帐户表中是否存在，存在则修改，不存在则添加
        Query query = new Query(Criteria.where("openId").is(reqDTO.getOpenId()));
        UserInfo userInfo = userInfoDao.findOne(query);
        if (userInfo == null){
            userInfo = JSON.parseObject(JSON.toJSONString(reqDTO), UserInfo.class);
            userInfo.setOpenId(reqDTO.getOpenId());
            userInfo.setLoginNum(1);
            userInfo.setCreateTime(now);
            userInfo.setUpdateTime(now);
            userInfoDao.save(userInfo);
        } else {
            userInfo.setNickName(reqDTO.getNickName());
            userInfo.setAvatarUrl(reqDTO.getAvatarUrl());
            userInfo.setGender(reqDTO.getGender());
            userInfo.setCountry(reqDTO.getCountry());
            userInfo.setProvince(reqDTO.getProvince());
            userInfo.setCity(reqDTO.getCity());
            userInfo.setLanguage(reqDTO.getLanguage());
            userInfo.setLoginNum(userInfo.getLoginNum() + 1);
            userInfo.setUpdateTime(now);
            userInfoDao.updateById(userInfo);
        }
        //登陆记录表添加登陆记录
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setOpenId(reqDTO.getOpenId());
        loginInfo.setNickName(reqDTO.getNickName());
        loginInfo.setLoginTime(now);
        loginInfoDao.save(loginInfo);
        //将token存入redis，登陆有效期24小时
        redisTemplate.opsForValue().set(RedisConstants.GO_ACTIVITY_USER_TOKEN + reqDTO.getOpenId(), token, 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(RedisConstants.GO_ACTIVITY_USER_USER_INFO + reqDTO.getOpenId(), JSON.toJSONString(userInfo), 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set(RedisConstants.GO_ACTIVITY_USER_PREFIX + token, reqDTO.getOpenId(), 24, TimeUnit.HOURS);

        LoginTokenRespDTO respDTO = new LoginTokenRespDTO();
        respDTO.setToken(token);
        context.setResult(respDTO);
        return context;
    }
}
