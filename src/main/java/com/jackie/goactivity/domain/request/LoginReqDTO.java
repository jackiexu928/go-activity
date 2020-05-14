package com.jackie.goactivity.domain.request;

import com.jackie.goactivity.common.enums.GoActivityCodeEnum;
import com.jackie.goactivity.exception.GoActivityException;
import domain.request.BaseReqDTO;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-08
 */
@Data
public class LoginReqDTO extends BaseReqDTO {
    private String openId;
    private String nickName;
    private String avatarUrl;
    private String gender;
    private String country;
    private String province;
    private String city;
    private String language;

    @Override
    public void validation() {
        if (StringUtils.isEmpty(openId)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
        if (StringUtils.isEmpty(nickName)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
    }
}
