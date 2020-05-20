package com.jackie.goactivity.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jackie.goactivity.common.enums.CostEnum;
import com.jackie.goactivity.common.enums.GoActivityCodeEnum;
import com.jackie.goactivity.dao.ActivityDetailDao;
import com.jackie.goactivity.dao.ActivityRecordDao;
import com.jackie.goactivity.dao.UserInfoDao;
import com.jackie.goactivity.domain.query.ActivityListQuery;
import com.jackie.goactivity.domain.request.ActivityAddReqDTO;
import com.jackie.goactivity.domain.request.BaseIdReqDTO;
import com.jackie.goactivity.domain.resopnse.AccountLoginRespDTO;
import com.jackie.goactivity.domain.resopnse.ActivityDetailRespDTO;
import com.jackie.goactivity.domain.resopnse.ActivityRecordRespDTO;
import com.jackie.goactivity.domain.resopnse.JoinAccountRespDTO;
import com.jackie.goactivity.entity.ActivityDetail;
import com.jackie.goactivity.entity.ActivityRecord;
import com.jackie.goactivity.entity.UserInfo;
import com.jackie.goactivity.exception.GoActivityException;
import com.jackie.goactivity.process.AbstractService;
import com.jackie.goactivity.process.Context;
import com.jackie.goactivity.util.ListUtil;
import com.jackie.goactivity.util.TrackHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-16
 */
@Service
public class ActivityService extends AbstractService {
    private static int TASK_LENGTH = 20;
    private static ThreadFactory namedThreadFactory =
            new ThreadFactoryBuilder().setNameFormat("ActivityService-pool-%d").build();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            TASK_LENGTH,
            TASK_LENGTH * 5,
            60 * 60 * 24,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(2000),
            namedThreadFactory
    );
    @Autowired
    private ActivityDetailDao activityDetailDao;
    @Autowired
    private ActivityRecordDao activityRecordDao;
    @Autowired
    private UserInfoDao userInfoDao;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
        activityDetail.setJoinNum(1);
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
        activityRecord.setActivityType(activity.getType());
        activityRecord.setType(1);
        activityRecord.setCreateId(accountLoginRespDTO.getOpenId());
        activityRecord.setCreateTime(now);
        activityRecord.setUpdateId(accountLoginRespDTO.getOpenId());
        activityRecord.setUpdateTime(now);
        activityRecordDao.save(activityRecord);
        return context;
    }

    public Context<ActivityListQuery, List<ActivityRecordRespDTO>> getRecordList(ActivityListQuery query){
        Context<ActivityListQuery, List<ActivityRecordRespDTO>> context = new Context<>();
        List<ActivityRecordRespDTO> list = new ArrayList<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Query recordQuery = new Query(Criteria.where("openId").is(accountLoginRespDTO.getOpenId()));
        if (query.getWillOrDone() == 1){
            recordQuery.addCriteria(Criteria.where("createTime").gte(new Date()));
        } else if (query.getWillOrDone() == 2){
            recordQuery.addCriteria(Criteria.where("createTime").lt(new Date()));
        }
        recordQuery.with(new Sort(Sort.Direction.DESC, "createTime"));
        List<ActivityRecord> recordList = activityRecordDao.find(recordQuery);
        if (ListUtil.isNotEmpty(recordList)){
            for (ActivityRecord record : recordList) {
                ActivityDetail activityDetail = activityDetailDao.findById(record.getActivityId());
                ActivityRecordRespDTO respDTO = new ActivityRecordRespDTO();
                respDTO.setRecordId(record.getId());
                respDTO.setActivityId(record.getActivityId());
                respDTO.setActivityType(record.getActivityType());
                respDTO.setType(record.getType());
                respDTO.setTheme(activityDetail.getTheme());
                respDTO.setStartTime(simpleDateFormat.format(activityDetail.getStartTime())
                        + activityDetail.getStartWeek());
                respDTO.setAddressName(activityDetail.getAddressName());
                respDTO.setLimitNum(activityDetail.getLimitNum());
                respDTO.setJoinNum(activityDetail.getJoinNum());
                list.add(respDTO);
            }
        }
        context.setResult(list);
        return context;
    }

    public Context<BaseIdReqDTO, ActivityDetailRespDTO> getActivityDetail(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, ActivityDetailRespDTO> context = new Context<>();
        ActivityDetailRespDTO respDTO = new ActivityDetailRespDTO();
        Date now = new Date();
        //查询活动
        ActivityDetail activityDetail = activityDetailDao.findById(reqDTO.getId());
        respDTO.setId(activityDetail.getId());
        respDTO.setTheme(activityDetail.getTheme());
        respDTO.setStartTime(simpleDateFormat.format(activityDetail.getStartTime())
                + activityDetail.getStartWeek());
        respDTO.setEndJoinTime(simpleDateFormat.format(activityDetail.getEndJoinTime())
                + activityDetail.getEndJoinWeek());
        respDTO.setAddressName(activityDetail.getAddressName());
        respDTO.setLatitude(activityDetail.getLatitude());
        respDTO.setLongitude(activityDetail.getLongitude());
        respDTO.setCostName(CostEnum.getNameByType(activityDetail.getCost()));
        respDTO.setCostRemark(activityDetail.getCostRemark());
        respDTO.setJoinNum(activityDetail.getJoinNum());
        respDTO.setLimitNum(activityDetail.getLimitNum());
        respDTO.setRemark(activityDetail.getRemark());
        if (now.before(activityDetail.getStartTime())) {
            respDTO.setWillOrDone(1);
        } else {
            respDTO.setWillOrDone(2);
        }
        context.setResult(respDTO);
        return context;
    }

    public Context<BaseIdReqDTO, List<JoinAccountRespDTO>> getJoinAccount(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, List<JoinAccountRespDTO>> context = new Context<>();
        List<JoinAccountRespDTO> list = new ArrayList<>();
        //查询活动
        ActivityDetail activityDetail = activityDetailDao.findById(reqDTO.getId());
        if (activityDetail.getJoinNum() > 0){
            //查询记录
            Query recordQuery = new Query(Criteria.where("activityId").is(reqDTO.getId()))
                    .with(new Sort(Sort.Direction.ASC, "createTime"));
            List<ActivityRecord> recordList = activityRecordDao.find(recordQuery);
            for (ActivityRecord record : recordList){
                Query userQuery = new Query(Criteria.where("openId").is(record.getOpenId()));
                UserInfo userInfo = userInfoDao.findOne(userQuery);
                JoinAccountRespDTO respDTO = new JoinAccountRespDTO();
                respDTO.setNickName(userInfo.getNickName());
                respDTO.setAvatarUrl(userInfo.getAvatarUrl());
                respDTO.setRole(record.getType());
                list.add(respDTO);
            }
        }
        context.setResult(list);
        return context;
    }
    /**
     * Map<Long, FutureTask<List<CountryHarmfulStatisticsVO>>> res = new HashMap<>();
     *         for (CountryHarmfulFormulaInfoRespDTO one : formulaList) {
     *             final CountryHarmfulFormulaInfoRespDTO rule = one;
     *             FutureTask<List<CountryHarmfulStatisticsVO>> task = new FutureTask<>(
     *                     new Callable<List<CountryHarmfulStatisticsVO>>() {
     *                         @Override
     *                         public List<CountryHarmfulStatisticsVO> call() {
     *                             String methodName = ClassMethodNameEnum.getQueryMethodByType(rule.getLocation());
     *                             return (List<CountryHarmfulStatisticsVO>) fetchDataInvoke(rule, query, methodName);
     *                         }
     *                     });
     *             res.put(one.getId(), task);
     *             executor.execute(task);
     *         }
     *
     *         Map<Long, List<CountryHarmfulStatisticsVO>> result = new HashMap<>();
     *         for (CountryHarmfulFormulaInfoRespDTO one : formulaList) {
     *             List<CountryHarmfulStatisticsVO> vo = this.getDataFromFutureTaskResponse(res, one.getId());
     *             result.put(one.getId(), vo);
     *         }
     */
    public Context<BaseIdReqDTO, Void> deleteActivity(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, Void> context = new Context<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Date now = new Date();
        ActivityDetail activityDetail = activityDetailDao.findById(reqDTO.getId());
        if (!activityDetail.getCreateId().equals(accountLoginRespDTO.getOpenId())){
            throw new GoActivityException(GoActivityCodeEnum.NO_JURISDICTION);
        }
        if (activityDetail.getStartTime().before(now)){
            throw new GoActivityException(GoActivityCodeEnum.ACTIVITY_IS_BEGIN);
        }
        Query query = new Query(Criteria.where("id").is(activityDetail.getId()));
        Update update = new Update();
        update.set("validFlag", 0);
        update.set("updateId", accountLoginRespDTO.getOpenId());
        update.set("updateTime", now);
        activityDetailDao.update(query, update);
        return context;
    }
}
