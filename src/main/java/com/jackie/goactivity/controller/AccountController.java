package com.jackie.goactivity.controller;

import com.jackie.goactivity.domain.request.LoginReqDTO;
import com.jackie.goactivity.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-08
 */
@RestController
@RequestMapping("account")
public class AccountController extends BaseController{
    @Autowired
    private AccountService accountService;

    @PostMapping("login")
    @ResponseBody
    public String login(LoginReqDTO reqDTO, HttpServletResponse response){
        return toJSON(accountService.login(reqDTO));
    }

    @GetMapping("test")
    public String test(String s){
        String str = "通过登陆校验";
        return str;
    }

}
