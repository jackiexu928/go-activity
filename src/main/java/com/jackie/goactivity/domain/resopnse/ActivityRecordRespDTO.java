package com.jackie.goactivity.domain.resopnse;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-18
 */
@Data
public class ActivityRecordRespDTO {
    /**
     * 记录id
     */
    private String recordId;
    /**
     * 活动ID
     */
    private String activityId;
    /**
     * 种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他，默认为其他
     */
    private Integer activityType;
    /**
     * 参与类型，1-组织者，2-参与者
     */
    private Integer type;
    /**
     * 主题
     */
    private String theme;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 活动地点
     */
    private String addressName;
    /**
     * 限制人数,-1为不限
     */
    private Integer limitNum;
    /**
     * 参加人数
     */
    private Integer joinNum;
}
