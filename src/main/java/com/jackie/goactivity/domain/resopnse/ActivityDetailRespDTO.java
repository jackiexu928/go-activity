package com.jackie.goactivity.domain.resopnse;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-19
 */
@Data
public class ActivityDetailRespDTO {
    /**
     * 活动ID
     */
    private String id;
    /**
     * 主题
     */
    private String theme;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 报名截止时间
     */
    private String endJoinTime;
    /**
     * 活动地点
     */
    private String addressName;
    /**
     * 纬度
     */
    private Double latitude;
    /**
     * 经度
     */
    private Double longitude;
    /**
     * 费用，1-免费，2-aa，3-自费，4-其他
     */
    private String costName;
    /**
     * 费用说明
     */
    private String costRemark;
    /**
     * 1-未开始，2-已开始
     * 未开始的可删除，已开始的可留言
     */
    private Integer willOrDone;
    /**
     * 报名人数
     */
    private Integer joinNum;
    /**
     * 限制人数
     */
    private Integer limitNum;
    /**
     * 活动说明
     */
    private String remark;
    /**
     * 参与人数
     */
    private List<AccountLoginRespDTO> joinList;
}
