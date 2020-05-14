package com.jackie.goactivity.controller;

import com.jackie.goactivity.process.Context;
import util.ResultMessageBuilder;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-04-29
 */
public class BaseController {
    private static final String DEFAULE_DATA_FOMATE = "yyyy-MM-dd HH:mm:ss";

    protected String toJSON(Context context) {
        if (!context.isSuccess()) {
            return ResultMessageBuilder.build(false, context.getErrorCode(), context.getErrorMsg()).toJSONString();
        } else {
            return ResultMessageBuilder.build(true, context.getErrorCode(), context.getErrorMsg(), context.getResult()).toJSONString();
        }
    }
    /**
     * 根据日期格式格式化返回数据
     * * @return
     */
    protected String toJSONByDateFormat(Context context, String dateFormat) {
        if (!context.isSuccess()) {
            return ResultMessageBuilder.build(false, context.getErrorCode(), context.getErrorMsg()).toJSONString();
        } else {
            return ResultMessageBuilder.build(context.getResult()).toJSONString(dateFormat);
        }
    }

    /**
     * 根据日期格式格式化返回数据
     *
     * @param context
     * @return
     */
    protected String toJSONByDateFormat(Context context) {
        return toJSONByDateFormat(context, DEFAULE_DATA_FOMATE);
    }

}
