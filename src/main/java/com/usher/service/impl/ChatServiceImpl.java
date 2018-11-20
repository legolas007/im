package com.usher.service.impl;

import com.usher.enums.MsgSignFlagEnum;
import com.usher.mapper.ChatMsgMapper;
import com.usher.mapper.UsersMapperCustom;
import com.usher.netty.NettyChatMsg;
import com.usher.pojo.ChatMsg;
import com.usher.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.n3r.idworker.Sid;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.List;

/**
 * @Author: Usher
 * @Description:
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private Sid sid;

    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Autowired
    private UsersMapperCustom usersMapperCustom;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveMsg(NettyChatMsg nettyChatMsg) {
        ChatMsg msg = new ChatMsg();
        String msgId = sid.nextShort();
        msg.setId(msgId);
        msg.setAcceptUserId(nettyChatMsg.getReceiverId());
        msg.setSendUserId(nettyChatMsg.getSenderId());
        msg.setCreateTime(new Date());
        msg.setSignFlag(MsgSignFlagEnum.unsign.type);
        msg.setMsg(nettyChatMsg.getMsg());

        chatMsgMapper.insert(msg);
        return msgId;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        usersMapperCustom.batchUpdateMsgSigned(msgIdList);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ChatMsg> getUnReadMsgList(String acceptUserId) {
        Example chatExample = new Example(ChatMsg.class);
        Criteria criteria = chatExample.createCriteria();
        criteria.andEqualTo("signFlag", 0);
        criteria.andEqualTo("acceptUserId", acceptUserId);

        return chatMsgMapper.selectByExample(chatExample);
    }
}
