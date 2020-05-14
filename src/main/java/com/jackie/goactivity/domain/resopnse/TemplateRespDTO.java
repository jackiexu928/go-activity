package com.jackie.goactivity.domain.resopnse;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-11
 */
@Data
public class TemplateRespDTO {
    private String id;
    /**
     * 模版类型，1-公共，2-私有
     */
    private Integer templateType;
    /**
     * 模版所有人，如果是公共的可为空
     */
    private String openId;
    /**
     * 模版名称
     */
    private String name;
    /**
     * 种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他
     */
    private Integer type;
    /**
     * 模版内容
     */
    private String detail;
}
