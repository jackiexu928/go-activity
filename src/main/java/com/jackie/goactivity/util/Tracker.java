package com.jackie.goactivity.util;

import com.jackie.goactivity.domain.resopnse.AccountLoginRespDTO;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-04-29
 */
public class Tracker {
    String openId;
    AccountLoginRespDTO accountLoginRespDTO;

    public Tracker() {
    }

    public Tracker(String openId, AccountLoginRespDTO accountLoginRespDTO) {
        this.openId=openId;
        this.accountLoginRespDTO=accountLoginRespDTO;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public AccountLoginRespDTO getAccountLoginRespDTO() {
        return accountLoginRespDTO;
    }

    public void setAccountLoginRespDTO(AccountLoginRespDTO accountLoginRespDTO) {
        this.accountLoginRespDTO = accountLoginRespDTO;
    }
}
