package com.jackie.goactivity.domain.request;

import com.jackie.goactivity.common.enums.GoActivityCodeEnum;
import com.jackie.goactivity.exception.GoActivityException;
import domain.request.BaseReqDTO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-12
 */
@Data
public class TemplateAddReqDTO extends BaseReqDTO {
    private Integer type;
    private String name;
    private String theme;
    private Integer limitNum;
    private Integer cost;
    private String remark;

    @Override
    public void validation() {
        if (type == null){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
    }
}
