package com.jackie.goactivity.service;

import com.jackie.goactivity.dao.ActivityDetailDao;
import com.jackie.goactivity.dao.ActivityRecordDao;
import com.jackie.goactivity.domain.request.ActivityAddReqDTO;
import com.jackie.goactivity.domain.resopnse.AccountLoginRespDTO;
import com.jackie.goactivity.entity.ActivityDetail;
import com.jackie.goactivity.entity.ActivityRecord;
import com.jackie.goactivity.exception.GoActivityException;
import com.jackie.goactivity.process.Context;
import com.jackie.goactivity.util.TrackHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    @Autowired
    private ActivityRecordDao activityRecordDao;

    public Context<ActivityAddReqDTO, Void> addActivity(ActivityAddReqDTO reqDTO){
        Context<ActivityAddReqDTO, Void> context = new Context<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        Date now = cal.getTime();
        //添加活动
        ActivityDetail activityDetail = new ActivityDetail();
        if (!StringUtils.isEmpty(reqDTO.getTemplateId())){
            activityDetail.setTemplateId(reqDTO.getTemplateId());
        }
        if (reqDTO.getType() == null){
            activityDetail.setType(reqDTO.getType());
        }
        activityDetail.setTheme(reqDTO.getTheme());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            activityDetail.setStartTime(simpleDateFormat.parse(year + "-" + reqDTO.getStartDate().substring(0, 6)
                    + reqDTO.getStartHour() + ":" + reqDTO.getStartMinute()));
            activityDetail.setEndJoinTime(simpleDateFormat.parse(year + "-" + reqDTO.getEndJoinDate().substring(0, 6)
                    + reqDTO.getEndJoinHour() + ":" + reqDTO.getEndJoinMinute()));
        } catch (Exception e){
            throw new GoActivityException(e);
        }
        activityDetail.setStartWeek(reqDTO.getStartDate().substring(6, 8));
        activityDetail.setEndJoinWeek(reqDTO.getEndJoinDate().substring(6, 8));
        if (reqDTO.getLimitNum() == null){
            activityDetail.setLimitNum(-1);
        }
        activityDetail.setJoinNum(0);
        activityDetail.setCost(reqDTO.getCost());
        if (reqDTO.getCost() == 4){
            activityDetail.setCostRemark(reqDTO.getCostRemark());
        }
        activityDetail.setAddress(reqDTO.getAddress());
        activityDetail.setAddressName(reqDTO.getAddressName());
        activityDetail.setLatitude(reqDTO.getLatitude());
        activityDetail.setLongitude(reqDTO.getLongitude());
        activityDetail.setDuty(reqDTO.getDuty());
        if (!StringUtils.isEmpty(reqDTO.getRemark())){
            activityDetail.setRemark(reqDTO.getRemark());
        }
        activityDetail.setValidFlag(1);
        activityDetail.setCreateId(accountLoginRespDTO.getOpenId());
        activityDetail.setCreateTime(now);
        activityDetail.setUpdateId(accountLoginRespDTO.getOpenId());
        activityDetail.setUpdateTime(now);
        ActivityDetail activity = activityDetailDao.save(activityDetail);
        //添加活动记录，用户为组织者
        ActivityRecord activityRecord = new ActivityRecord();
        activityRecord.setOpenId(accountLoginRespDTO.getOpenId());
        activityRecord.setActivityId(activity.getId());
        activityRecord.setType(1);
        activityRecord.setCreateId(accountLoginRespDTO.getOpenId());
        activityRecord.setCreateTime(now);
        activityRecord.setUpdateId(accountLoginRespDTO.getOpenId());
        activityRecord.setUpdateTime(now);
        activityRecordDao.save(activityRecord);
        return context;
    }
}
