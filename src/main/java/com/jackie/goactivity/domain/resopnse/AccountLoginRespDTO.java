package com.jackie.goactivity.domain.resopnse;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-04-29
 */
@Data
public class AccountLoginRespDTO {
    private String openId;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 性别：0-未知，1-男，2-女
     */
    private String gender;
    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 语言：en-英文，zh_CN-简体中文，zh_TW-繁体中文
     */
    private String language;
    /**
     * 登陆次数
     */
    private Integer loginNum;
    private Date createTime;
    private Date updateTime;
}
