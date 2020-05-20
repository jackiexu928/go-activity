package com.jackie.goactivity.common.enums;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * Description:
 * 1-免费，2-aa，3-自费，4-其他
 *
 * @author xujj
 * @date 2020-05-19
 */
public enum CostEnum {
    FREE(1, "免费"), AA(2, "AA"), SELF(3, "自费"), OTHER(4, "其他");

    private int type;
    private String name;

    CostEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String getNameByType(int type){
        CostEnum[] enums = CostEnum.values();
        for (CostEnum costEnum : enums){
            if (costEnum.getType() == type){
                return costEnum.getName();
            }
        }
        return null;
    }
}
