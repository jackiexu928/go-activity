package com.jackie.goactivity.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Description: 登陆记录
 *
 * @author xujj
 * @date 2020-05-09
 */
@Data
@Document(collection="login_info")
public class LoginInfo extends BaseEntity{
    @Id
    private String id;
    @NotNull
    private String openId;
    private String nickName;
    private Date loginTime;
}
