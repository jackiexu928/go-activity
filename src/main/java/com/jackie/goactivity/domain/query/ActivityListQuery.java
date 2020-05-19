package com.jackie.goactivity.domain.query;

import domain.request.BaseReqDTO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-18
 */
@Data
public class ActivityListQuery extends BaseReqDTO {
    /**
     * 未开始或者已开始，1-未开始，2-已开始
     */
    private Integer willOrDone;
    /**
     * 参与类型：1-组织者，2-参与者
     */
    private Integer type;

    @Override
    public void validation() {

    }
}
