package com.jackie.goactivity.dao.impl;

import com.jackie.goactivity.dao.ActivityRecordDao;
import com.jackie.goactivity.dao.mongodb.MongoBaseDaoImpl;
import com.jackie.goactivity.entity.ActivityRecord;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-17
 */
@Repository
public class ActivityRecordDaoImpl extends MongoBaseDaoImpl<ActivityRecord> implements ActivityRecordDao {
}
