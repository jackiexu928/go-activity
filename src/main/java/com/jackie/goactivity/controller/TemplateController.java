package com.jackie.goactivity.controller;

import com.jackie.goactivity.domain.query.BaseVoidQuery;
import com.jackie.goactivity.domain.request.BaseIdReqDTO;
import com.jackie.goactivity.domain.request.TemplateAddReqDTO;
import com.jackie.goactivity.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-11
 */
@RestController
@RequestMapping("template")
public class TemplateController extends BaseController {
    @Autowired
    private TemplateService templateService;

    @GetMapping("getList")
    @ResponseBody
    public String getList(BaseVoidQuery query){
        return toJSON(templateService.getTemplateList(query));
    }

    @PostMapping("addTemplate")
    @ResponseBody
    public String addTemplate(TemplateAddReqDTO reqDTO){
        return toJSON(templateService.addTemplate(reqDTO));
    }

    @PostMapping("addTemplate")
    @ResponseBody
    public String deleteTemplate(BaseIdReqDTO reqDTO){
        return null;
    }
}
