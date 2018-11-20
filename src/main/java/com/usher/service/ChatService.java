package com.usher.service;



import com.usher.netty.NettyChatMsg;
import com.usher.pojo.ChatMsg;

import java.util.List;

/**
 * @Author: Usher
 * @Description:
 */
public interface ChatService {
    /**
     * 保存消息到数据库
     * @param nettyChatMsg
     * @return
     */
    String saveMsg(NettyChatMsg nettyChatMsg);
    /**
     * @Description: 批量签收消息
     */
    void updateMsgSigned(List<String> msgIdList);

    /**
     * 获取未读消息列表
     * @param acceptUserId
     * @return
     */
    List<ChatMsg> getUnReadMsgList(String acceptUserId);
}
