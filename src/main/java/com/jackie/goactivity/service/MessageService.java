package com.jackie.goactivity.service;

import com.jackie.goactivity.common.enums.GoActivityCodeEnum;
import com.jackie.goactivity.dao.MessageDao;
import com.jackie.goactivity.domain.request.BaseIdReqDTO;
import com.jackie.goactivity.domain.request.MessageAddReqDTO;
import com.jackie.goactivity.domain.request.MessageUpdateReqDTO;
import com.jackie.goactivity.domain.resopnse.AccountLoginRespDTO;
import com.jackie.goactivity.domain.resopnse.MessageRespDTO;
import com.jackie.goactivity.entity.Message;
import com.jackie.goactivity.exception.GoActivityException;
import com.jackie.goactivity.process.Context;
import com.jackie.goactivity.util.ListUtil;
import com.jackie.goactivity.util.TrackHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-20
 */
@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;

    public Context<BaseIdReqDTO, List<MessageRespDTO>> getMessageList(BaseIdReqDTO reqDTO){
        Context<BaseIdReqDTO, List<MessageRespDTO>> context = new Context<>();
        List<MessageRespDTO> respList = new ArrayList<>();
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Query selfQuery = new Query(Criteria.where("activityId").is(reqDTO.getId()))
                .with(new Sort(Sort.Direction.ASC,"createTime"));
        List<Message> selfList = messageDao.find(selfQuery);
        if (ListUtil.isNotEmpty(selfList)){
            for (Message message : selfList){
                //不是自己的非公开留言则不显示
                if (!message.getOpenId().equals(accountLoginRespDTO.getOpenId()) && message.getOpen() == 0){
                    continue;
                }
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
