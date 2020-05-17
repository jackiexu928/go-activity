package com.jackie.goactivity.service;

import com.jackie.goactivity.dao.ActivityDetailDao;
import com.jackie.goactivity.domain.request.ActivityAddReqDTO;
import com.jackie.goactivity.process.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-16
 */
@Service
public class ActivityService {
    @Autowired
    private ActivityDetailDao activityDetailDao;

    public Context<ActivityAddReqDTO, Void> addActivity(ActivityAddReqDTO reqDTO){
        Context<ActivityAddReqDTO, Void> context = new Context<>();
        //添加活动

        //添加活动记录，用户为组织者

        return context;
    }
}
