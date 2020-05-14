package com.jackie.goactivity.util;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-09
 */
public class UuidUtil {
    public static String getUuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getUUidNoSplit(){
        UUID uuid=UUID.randomUUID();
        return uuid.toString().replaceAll("-","");
    }
}
