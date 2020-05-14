package com.jackie.goactivity.common.enums;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-09
 */
public enum GoActivityCodeEnum {
    PARAM_ISNULL("PARAM_ISNULL","参数为空"),
    USER_NOT_LOGIN("USER_NOT_LOGIN", "用户未登录");

    private String errMsg;

    private String errCode;

    private GoActivityCodeEnum(String errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private GoActivityCodeEnum(String errCode){
        this.errCode = errCode;
    }

    public static GoActivityCodeEnum getTbcpErrorCodeEnum(String code) {
        for(GoActivityCodeEnum x: GoActivityCodeEnum.values()) {
            if(x.getErrCode().equals(code)) {
                return x;
            }
        }
        return null;
    }

    public String getErrCode(){
        return this.errCode;
    }

    public String getErrMsg(){
        return this.errMsg;
    }
}
