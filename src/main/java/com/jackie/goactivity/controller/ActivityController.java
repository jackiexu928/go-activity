package com.jackie.goactivity.controller;

import com.jackie.goactivity.domain.request.ActivityAddReqDTO;
import com.jackie.goactivity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-16
 */
@RestController
@RequestMapping("activity")
public class ActivityController extends BaseController {
    @Autowired
    private ActivityService activityService;

    @PostMapping("addActivity")
    @ResponseBody
    public String addActivity(ActivityAddReqDTO reqDTO){
        return toJSON(activityService.addActivity(reqDTO));
    }
}
