package com.jackie.goactivity.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jackie.goactivity.common.enums.GoActivityCodeEnum;
import com.jackie.goactivity.dao.MessageDao;
import com.jackie.goactivity.dao.UserInfoDao;
import com.jackie.goactivity.domain.request.BaseIdReqDTO;
import com.jackie.goactivity.domain.request.MessageAddReqDTO;
import com.jackie.goactivity.domain.request.MessageUpdateReqDTO;
import com.jackie.goactivity.domain.resopnse.AccountLoginRespDTO;
import com.jackie.goactivity.domain.resopnse.MessageRespDTO;
import com.jackie.goactivity.entity.Message;
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
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-20
 */
@Service
public class MessageService extends AbstractService {
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
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserInfoDao userInfoDao;

    public Context<BaseIdReqDTO, List<MessageRespDTO>> getMessageList(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, List<MessageRespDTO>> context = new Context<>();
        List<MessageRespDTO> respList = new ArrayList<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Query selfQuery = new Query(Criteria.where("activityId").is(reqDTO.getId()))
                .with(new Sort(Sort.Direction.ASC,"createTime"));
        List<Message> selfList = messageDao.find(selfQuery);
        if (ListUtil.isNotEmpty(selfList)){
            Map<String, FutureTask<MessageRespDTO>> res = new HashMap<>();
            for (Message one : selfList){
                //不是自己的非公开留言则不显示
                if (!one.getOpenId().equals(accountLoginRespDTO.getOpenId()) && one.getOpen() == 0){
                    return null;
                }
                final Message message = one;
                FutureTask<MessageRespDTO> task = new FutureTask<>(new Callable<MessageRespDTO>() {
                    @Override
                    public MessageRespDTO call() throws Exception {
                        MessageRespDTO respDTO = new MessageRespDTO();
                        respDTO.setId(message.getId());
                        respDTO.setActivityId(message.getActivityId());
                        respDTO.setOpen(message.getOpen());
                        respDTO.setContent(message.getContent());
                        if (message.getOpenId().equals(accountLoginRespDTO.getOpenId())) {
                            respDTO.setSelf(1);
                        } else {
                            respDTO.setSelf(0);
                        }
                        Query query = new Query(Criteria.where("openId").is(message.getOpenId()));
                        UserInfo userInfo = userInfoDao.findOne(query);
                        respDTO.setNickName(userInfo.getNickName());
                        respDTO.setAvatarUrl(userInfo.getAvatarUrl());
                        respDTO.setCreateTime(simpleDateFormat.format(message.getCreateTime()));
                        return respDTO;
                    }
                });
                res.put(one.getId(), task);
                executor.execute(task);
            }
            for (Message one : selfList){
                //不是自己的非公开留言则不显示
                if (!one.getOpenId().equals(accountLoginRespDTO.getOpenId()) && one.getOpen() == 0){
                    return null;
                }
                respList.add(this.getDataFromFutureTaskResponse(res, one.getId()));
            }
        }
        context.setResult(respList);
        return context;
    }
    private MessageRespDTO getDataFromFutureTaskResponse(Map<String, FutureTask<MessageRespDTO>> res, String id){
        if (CollectionUtils.isEmpty(res)){
            return null;
        }
        FutureTask<MessageRespDTO> response = res.get(id);
        try {
            if (response == null){
                return null;
            } else {
                return response.get();
            }
        } catch (Exception e){

        }
        return null;
    }

    public Context<BaseIdReqDTO, List<MessageRespDTO>> getMyMessageList(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, List<MessageRespDTO>> context = new Context<>();
        List<MessageRespDTO> respList = new ArrayList<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Query selfQuery = new Query(Criteria.where("activityId").is(reqDTO.getId())
                .and("openId").is(accountLoginRespDTO.getOpenId()))
                .with(new Sort(Sort.Direction.ASC,"createTime"));
        List<Message> selfList = messageDao.find(selfQuery);
        if (ListUtil.isNotEmpty(selfList)){
            for (Message message : selfList){
                MessageRespDTO respDTO = new MessageRespDTO();
                respDTO.setId(message.getId());
                respDTO.setActivityId(message.getActivityId());
                respDTO.setOpen(message.getOpen());
                respDTO.setContent(message.getContent());
                if (message.getOpenId().equals(accountLoginRespDTO.getOpenId())) {
                    respDTO.setSelf(1);
                } else {
                    respDTO.setSelf(0);
                }
                respList.add(respDTO);
            }
        }
        context.setResult(respList);
        return context;
    }

    public Context<MessageAddReqDTO, Void> addMessage(MessageAddReqDTO reqDTO){
        Context<MessageAddReqDTO, Void> context = new Context<>();
        Date now = new Date();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Message message = new Message();
        message.setActivityId(reqDTO.getActivityId());
        message.setOpenId(accountLoginRespDTO.getOpenId());
        message.setOpen(reqDTO.getOpen());
        message.setContent(reqDTO.getContent());
        message.setValidFlag(1);
        message.setCreateId(accountLoginRespDTO.getOpenId());
        message.setUpdateId(accountLoginRespDTO.getOpenId());
        message.setCreateTime(now);
        message.setUpdateTime(now);
        messageDao.save(message);
        return context;
    }

    public Context<BaseIdReqDTO, Void> deleteMessage(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, Void> context = new Context<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Message message = messageDao.findById(reqDTO.getId());
        if (message != null){
            if (!message.getOpenId().equals(accountLoginRespDTO.getOpenId())){
                throw new GoActivityException(GoActivityCodeEnum.NO_JURISDICTION);
            }
            Query query = new Query(Criteria.where("id").is(message.getId()));
            Update update = new Update();
            update.set("validFlag", 0);
            update.set("updateId", accountLoginRespDTO.getOpenId());
            update.set("UpdateTime", new Date());
            messageDao.update(query, update);
        }
        return context;
    }

    public Context<MessageUpdateReqDTO, Void> updateMessage(MessageUpdateReqDTO reqDTO){
        Context<MessageUpdateReqDTO, Void> context = new Context<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Message message = messageDao.findById(reqDTO.getId());
        if (message != null){
            if (!message.getOpenId().equals(accountLoginRespDTO.getOpenId())){
                throw new GoActivityException(GoActivityCodeEnum.NO_JURISDICTION);
            }
            Query query = new Query(Criteria.where("id").is(message.getId()));
            Update update = new Update();
            update.set("content", reqDTO.getContent());
            update.set("updateId", accountLoginRespDTO.getOpenId());
            update.set("UpdateTime", new Date());
            messageDao.update(query, update);
        }
        return context;
    }
}
