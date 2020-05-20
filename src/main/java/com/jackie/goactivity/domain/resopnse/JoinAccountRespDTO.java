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
public class JoinAccountRespDTO {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatarUrl;
    /**
     * 身份，1-组织者，2-参与者
     */
    private Integer role;
}
