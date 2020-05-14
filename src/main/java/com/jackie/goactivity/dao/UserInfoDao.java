package com.jackie.goactivity.dao;

import com.jackie.goactivity.dao.mongodb.MongoBaseDao;
import com.jackie.goactivity.entity.UserInfo;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-09
 */
public interface UserInfoDao extends MongoBaseDao<UserInfo> {
    void updateById(UserInfo userInfo);
}
