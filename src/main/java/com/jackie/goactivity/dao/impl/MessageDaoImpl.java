package com.jackie.goactivity.dao.impl;

import com.jackie.goactivity.dao.MessageDao;
import com.jackie.goactivity.dao.mongodb.MongoBaseDaoImpl;
import com.jackie.goactivity.entity.Message;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-20
 */
@Repository
public class MessageDaoImpl extends MongoBaseDaoImpl<Message> implements MessageDao {
}
