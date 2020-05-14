package com.jackie.goactivity.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-11
 */
@RestController
@RequestMapping("backDoor")
public class BackDoorController extends BaseController {

    @PostMapping("addTemplate")
    public String addTemplate(){
        return null;
    }
}
