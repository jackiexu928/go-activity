package com.jackie.goactivity.controller;

import com.jackie.goactivity.domain.request.BaseIdReqDTO;
import com.jackie.goactivity.domain.request.MessageAddReqDTO;
import com.jackie.goactivity.domain.request.MessageUpdateReqDTO;
import com.jackie.goactivity.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-05-20
 */
@RestController
@RequestMapping("message")
public class MessageController extends BaseController {
    @Autowired
    private MessageService messageService;

    /**
     * 获取活动留言
     * @param reqDTO
     * @return
     */
    @GetMapping("getMessageList")
    @ResponseBody
    public String getMessageList(BaseIdReqDTO reqDTO){
        return toJSON(messageService.getMessageList(reqDTO));
    }

    /**
     * 获取自己的留言
     * @param reqDTO
     * @return
     */
    @GetMapping("getMyMessageList")
    @ResponseBody
    public String getMyMessageList(BaseIdReqDTO reqDTO){
        return toJSON(messageService.getMyMessageList(reqDTO));
    }

    /**
     * 添加留言
     * @param reqDTO
     * @return
     */
    @PostMapping("addMessage")
    @ResponseBody
    public String addMessage(MessageAddReqDTO reqDTO){
        return toJSON(messageService.addMessage(reqDTO));
    }

    /**
     * 删除留言
     * @param reqDTO
     * @return
     */
    @PostMapping("deleteMessage")
    @ResponseBody
    public String delMessage(BaseIdReqDTO reqDTO){
        return toJSON(messageService.deleteMessage(reqDTO));
    }

    /**
     * 修改留言
     * @param reqDTO
     * @return
     */
    @PostMapping("updateMessage")
    @ResponseBody
    public String updateMessage(MessageUpdateReqDTO reqDTO){
        return toJSON(messageService.updateMessage(reqDTO));
    }
}
