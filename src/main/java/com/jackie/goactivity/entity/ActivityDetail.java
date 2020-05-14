package com.jackie.goactivity.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-11
 */
@Data
@Document(collection = "activity_detail")
public class ActivityDetail extends BaseEntity{
    @Id
    private String id;
    /**
     * 模版id
     */
    private String templateId;
    /**
     * 种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他，默认为其他
     */
    private Integer type;
    /**
     * 主题
     */
    private String theme;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 报名截止时间
     */
    private Date endJoinTime;
    /**
     * 限制人数
     */
    private Integer limitNum;
    /**
     * 参加人数
     */
    private Integer joinNum;
    /**
     * 费用，1-免费，2-aa，3-自费，4-其他
     */
    private Integer cost;
    /**
     * 费用说明
     */
    private String costRemark;
    /**
     * 地点
     */
    private String address;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 免责，0-未勾选（前端需校验）1-已勾选
     */
    private Integer duty;
    /**
     * 活动说明
     */
    private String remark;
    /**
     * 是否有效，0-已删除，1-有效
     */
    private Integer validFlag;
}
