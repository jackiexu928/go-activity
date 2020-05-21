package com.jackie.goactivity.domain.resopnse;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-20
 */
@Data
public class MessageRespDTO {
    private String id;
    /**
     * 活动ID
     */
    private String activityId;
    private String nickName;
    private String avatarUrl;
    /**
     * 是否公开，0-仅自己可见，1-公开
     * 查询时，会优先展示自己的留言
     */
    private Integer open;
    /**
     * 留言内容
     */
    private String content;
    /**
     * 是否为自己的，1-是，0-否
     */
    private Integer self;
    private String createTime;
}
