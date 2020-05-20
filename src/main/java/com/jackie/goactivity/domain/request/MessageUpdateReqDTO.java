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
 * @date 2020-05-20
 */
@Data
public class MessageUpdateReqDTO extends BaseReqDTO {
    private String id;
    private String content;

    @Override
    public void validation() {
        if (StringUtils.isEmpty(id)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
        if (StringUtils.isEmpty(content)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
    }
}
