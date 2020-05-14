package com.jackie.goactivity.process;

import com.alibaba.fastjson.JSON;
import domain.request.BaseReqDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import util.LogUtil;


/**
 * Created by Administrator on 2018/3/12 0012.
 * 更新类操作抽象类
 */
public abstract class AbstractService <P extends BaseReqDTO,M> implements CommonInterface<P, M>{
    protected final static Logger logger = LoggerFactory.getLogger("Service");

    @Override
    public void onStarted(Context<P, M> context) {
        //数据校验
    	if(context.getParam()!=null) {
    		context.getParam().validation();
    	}
    }

    @Override
    public void onSuccess(Context<P, M> context) {
        //如果有业务错误，需要抛出异常
        context.setSuccess(true);
    }

    @Override
    public void onError(Context<P, M> context, Throwable e) {
        LogUtil.info(logger, context.getClassName() + "." + context.getMethodName(), context.getParam(), e);
        /*logger.info("[param]:" + (context.getParam()==null?"":context.getParam()) +
                "，[class]:" + context.getClassName() +
                ",[method]:" + context.getMethodName() + e);*/
        if(StringUtils.isEmpty(context.getErrorMsg())){
            context.setErrorMsg(e.getMessage());
        }
        context.setSuccess(false);
    }

    @Override
    public void onEnd(Context<P, M> context) {
        LogUtil.info(logger, context.getClassName() + "." + context.getMethodName(), context.getParam(),
                JSON.toJSONString(context.getResult()));
        //打印入参和出参
        /*CollectionLog.record(context.getClassName(), context.getMethodName(),
                context.getResult(),
                context.getParam(),
                logger);*/
//        TrackHolder.remove();
        /*if(!context.getMethodName().contains("exportPDFHos")) {
            TrackHolder.remove();
        }*/
    }
}
