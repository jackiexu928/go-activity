package com.jackie.goactivity.dao.impl;

import com.jackie.goactivity.dao.UserInfoDao;
import com.jackie.goactivity.dao.mongodb.MongoBaseDaoImpl;
import com.jackie.goactivity.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-09
 */
@Repository
public class UserInfoDaoImpl extends MongoBaseDaoImpl<UserInfo> implements UserInfoDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateById(UserInfo userInfo) {
        Query query = new Query(Criteria.where("id").is(userInfo.getId()));
        Update update = new Update();
        update.set("nickName", userInfo.getNickName());
        update.set("avatarUrl", userInfo.getAvatarUrl());
        update.set("gender", userInfo.getGender());
        update.set("country", userInfo.getCountry());
        update.set("province", userInfo.getProvince());
        update.set("city", userInfo.getCity());
        update.set("language", userInfo.getLanguage());
        update.set("loginNum", userInfo.getLoginNum());
        update.set("updateTime", userInfo.getUpdateTime());
        mongoTemplate.updateFirst(query, update, UserInfo.class);
    }
}
