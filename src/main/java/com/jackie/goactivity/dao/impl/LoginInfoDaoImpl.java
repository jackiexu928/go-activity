package com.jackie.goactivity.dao.impl;

import com.jackie.goactivity.dao.LoginInfoDao;
import com.jackie.goactivity.dao.mongodb.MongoBaseDaoImpl;
import com.jackie.goactivity.entity.LoginInfo;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-09
 */
@Repository
public class LoginInfoDaoImpl extends MongoBaseDaoImpl<LoginInfo> implements LoginInfoDao {
}
