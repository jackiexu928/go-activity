package com.jackie.goactivity.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-19
 */
@Data
@Document(collection = "message")
public class Message extends BaseEntity {
    @Id
    private String id;
    /**
     * 活动ID
     */
    private String activityId;
    /**
     * 用户ID
     */
    private String openId;
    /**
     * 是否公开，0-仅自己可见，1-公开
     * 查询时，会优先展示自己的留言
     */
    private Integer open;
    /**
     * 0-失效，1-有效
     */
    private Integer validFlag;
}
