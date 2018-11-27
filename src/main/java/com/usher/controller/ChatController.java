package com.usher.controller;

import com.usher.pojo.ChatMsg;
import com.usher.service.ChatService;
import com.usher.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Usher
 * @Description:
 */
@RestController
@RequestMapping("u")
@CrossOrigin
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;
    @PostMapping("/getUnReadMsgList")
    public JsonResult getUnReadMsgList(String acceptUserId) {
        if (StringUtils.isBlank(acceptUserId)) {
            return JsonResult.errorMsg("");
        }

        List<ChatMsg> unreadMsgList = chatService.getUnReadMsgList(acceptUserId);
        return JsonResult.ok(unreadMsgList);
    }
}
