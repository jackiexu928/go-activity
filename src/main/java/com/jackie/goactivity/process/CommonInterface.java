package com.jackie.goactivity.process;

/**
 * Created by Administrator on 2018/3/12 0012.
 * 公共接口定义
 */
public interface CommonInterface<P,M> {

    /**
     * 业务方法开始前
     * @param context
     */
    void onStarted(Context<P, M> context);

    /**
     * 业务方法正常结束
     * @param context
     */
    void onSuccess(Context<P, M> context);

    /**
     * 业务方法异常
     * @param context
     */
    void onError(Context<P, M> context, Throwable e);

    /**
     * 业务方法结束
     * @param context
     */
    void onEnd(Context<P, M> context);
}
