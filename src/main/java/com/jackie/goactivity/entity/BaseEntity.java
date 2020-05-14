package com.jackie.goactivity.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-09
 */
@Data
public class BaseEntity {
    private String createId;
    private String updateId;
    private Date createTime;
    private Date updateTime;
}
