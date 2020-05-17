package com.jackie.goactivity.dao.impl;

import com.jackie.goactivity.dao.ActivityDetailDao;
import com.jackie.goactivity.dao.mongodb.MongoBaseDaoImpl;
import com.jackie.goactivity.entity.ActivityDetail;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-17
 */
@Repository
public class ActivityDetailDaoImpl extends MongoBaseDaoImpl<ActivityDetail> implements ActivityDetailDao {
}
