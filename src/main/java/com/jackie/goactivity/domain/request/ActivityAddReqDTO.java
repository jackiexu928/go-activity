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
 * @date 2020-05-16
 */
@Data
public class ActivityAddReqDTO extends BaseReqDTO {
    /**
     * 模版id
     */
    private String templateId;
    /**
     * 种类，1-运动，2-户外，3-聚餐，4-桌游，5-娱乐活动，6-读书会，7-其他，默认为其他
     */
    private Integer type;
    /**
     * 主题
     */
    private String theme;
    /**
     * 开始时间
     */
    private String startDate;
    private String startHour;
    private String startMinute;
    /**
     * 报名截止时间
     */
    private String endJoinDate;
    private String endJoinHour;
    private String endJoinMinute;
    /**
     * 限制人数
     */
    private Integer limitNum;
    /**
     * 费用，1-免费，2-aa，3-自费，4-其他
     */
    private Integer cost;
    /**
     * 费用说明
     */
    private String costRemark;
    /**
     * 地点
     */
    private String address;
    /**
     * 地点名称
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
     * 免责，0-未勾选（前端需校验）1-已勾选
     */
    private Integer duty;
    /**
     * 活动说明
     */
    private String remark;

    @Override
    public void validation() {
        if (StringUtils.isEmpty(theme)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
        if (StringUtils.isEmpty(startDate)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
        if (StringUtils.isEmpty(startHour) || StringUtils.isEmpty(startMinute)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
        if (StringUtils.isEmpty(endJoinDate)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
        if (StringUtils.isEmpty(endJoinHour) || StringUtils.isEmpty(endJoinMinute)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
        if (cost == 4 && StringUtils.isEmpty(costRemark)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
        if (StringUtils.isEmpty(address)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
        if (StringUtils.isEmpty(addressName)){
            throw new GoActivityException(GoActivityCodeEnum.PARAM_ISNULL);
        }
        if (latitude == null || latitude == 0){
            throw new GoActivityException(GoActivityCodeEnum.ILLGAL_ARGUMENT);
        }
        if (longitude == null || longitude == 0){
            throw new GoActivityException(GoActivityCodeEnum.ILLGAL_ARGUMENT);
        }
        if (duty == null){
            throw new GoActivityException(GoActivityCodeEnum.ILLGAL_ARGUMENT);
        }
    }
}
