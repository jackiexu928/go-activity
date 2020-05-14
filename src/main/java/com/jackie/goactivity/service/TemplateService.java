package com.jackie.goactivity.service;

import com.alibaba.fastjson.JSON;
import com.jackie.goactivity.dao.TemplateDao;
import com.jackie.goactivity.domain.query.BaseVoidQuery;
import com.jackie.goactivity.domain.request.TemplateAddReqDTO;
import com.jackie.goactivity.domain.resopnse.AccountLoginRespDTO;
import com.jackie.goactivity.domain.resopnse.TemplateRespDTO;
import com.jackie.goactivity.entity.ActivityDetail;
import com.jackie.goactivity.entity.Template;
import com.jackie.goactivity.process.AbstractService;
import com.jackie.goactivity.process.Context;
import com.jackie.goactivity.util.ListUtil;
import com.jackie.goactivity.util.TrackHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-11
 */
@Service
public class TemplateService extends AbstractService {
    @Autowired
    private TemplateDao templateDao;

    public Context<BaseVoidQuery, List<TemplateRespDTO>> getTemplateList(BaseVoidQuery query){
        Context<BaseVoidQuery, List<TemplateRespDTO>> context = new Context<>();
        //获取登陆信息
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        //先查询公有的模版，按type排序
        Query publicQuery = new Query(Criteria.where("templateType").is(1))
                .with(new Sort(Sort.Direction.ASC, "type"));
        List<Template> publicList = templateDao.find(publicQuery);
        //查询私有模版，无序
        Query privateQuery = new Query(Criteria.where("templateType").is(2)
                .and("openId").is(accountLoginRespDTO.getOpenId()));
        List<Template> privateList = templateDao.find(privateQuery);
        List<TemplateRespDTO> respList = new ArrayList<>();
        respList.addAll(JSON.parseArray(JSON.toJSONString(publicList), TemplateRespDTO.class));
        if (ListUtil.isNotEmpty(privateList)){
            respList.addAll(JSON.parseArray(JSON.toJSONString(privateList), TemplateRespDTO.class));
        }
        context.setResult(respList);
        return context;
    }

    public Context<TemplateAddReqDTO, Void> addTemplate(TemplateAddReqDTO reqDTO){
        Context<TemplateAddReqDTO, Void> context = new Context<>();
        Date now = new Date();
        //获取登陆信息
        AccountLoginRespDTO accountLoginRespDTO = TrackHolder.getTracker().getAccountLoginRespDTO();
        Template template = new Template();
        template.setOpenId(accountLoginRespDTO.getOpenId());
        template.setTemplateType(2);
        template.setType(reqDTO.getType());
        template.setName(reqDTO.getName());
        template.setCreateId(accountLoginRespDTO.getOpenId());
        template.setUpdateId(accountLoginRespDTO.getOpenId());
        template.setCreateTime(now);
        template.setUpdateTime(now);
        if (!(StringUtils.isEmpty(reqDTO.getTheme()) && StringUtils.isEmpty(reqDTO.getRemark())
                && null == reqDTO.getLimitNum() && null == reqDTO.getCost())){
            ActivityDetail detail = new ActivityDetail();
            detail.setTheme(reqDTO.getTheme());
            detail.setLimitNum(reqDTO.getLimitNum());
            detail.setCost(reqDTO.getCost());
            detail.setRemark(reqDTO.getRemark());
            template.setDetail(JSON.toJSONString(detail));
        }
        templateDao.save(template);
        return context;
    }
}
