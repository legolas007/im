package com.usher.netty;

import com.usher.SpringUtil;
import com.usher.enums.MsgActionEnum;
import com.usher.service.ChatService;
import com.usher.service.UserService;
import com.usher.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Usher
 * @Description:处理消息的handler
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 用于记录和管理所有客户端的channle
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //获取客户端传输的消息
        String content = textWebSocketFrame.text();

        //System.out.println("接收的数据：" + content);
        Channel curChannel = channelHandlerContext.channel();
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();

        if (action.equals(MsgActionEnum.CONNECT.type)) {
            String senderId = dataContent.getNettyChatMsg().getSenderId();
            UserChannelRel.put(senderId, curChannel);

            //test
            for (Channel channel : clients) {
                System.out.println(channel.id().asLongText());
            }
            UserChannelRel.output();
        } else if (action.equals(MsgActionEnum.CHAT.type)) {
            NettyChatMsg nettyChatMsg = dataContent.getNettyChatMsg();
            String msgText = nettyChatMsg.getMsg();
            String receiverId = nettyChatMsg.getReceiverId();
            String senderId = nettyChatMsg.getSenderId();

            // 保存消息到数据库，并且标记为 未签收
            ChatService chatService = (ChatService) SpringUtil.getBean("chatServiceImpl");
            String msgId = chatService.saveMsg(nettyChatMsg);
            nettyChatMsg.setMsgId(msgId);

            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setNettyChatMsg(nettyChatMsg);
            // 发送消息
            // 从全局用户Channel关系中获取接受方的channel
            Channel receiverChannel = UserChannelRel.get(receiverId);
            if (receiverChannel == null) {
                //TODO channel为空代表用户离线,推送消息
            } else {
                Channel findChannel =clients.find(receiverChannel.id());
                if (findChannel != null) {
                    // 用户在线
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContentMsg)));
                } else {
                    // 用户离线 TODO 推送消息
                }
            }
        } else if (action.equals(MsgActionEnum.SIGNED.type)) {
            ChatService chatService = (ChatService) SpringUtil.getBean("chatServiceImpl");
            //签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]
            String msgIdsStr = dataContent.getExtend();
            String msgIds[] = msgIdsStr.split(",");

            List<String> msgIdList = new ArrayList<>();
            for (String mid : msgIds) {
                if (StringUtils.isNotBlank(mid)) {
                    msgIdList.add(mid);
                }
            }

            System.out.println(msgIdList.toString());
            if (!msgIdList.isEmpty()) {
                //批量签收
                chatService.updateMsgSigned(msgIdList);
            }
        } else if (action.equals(MsgActionEnum.KEEPALIVE.type)) {
            //  2.4  心跳类型的消息
            System.out.println("收到来自channel为[" + curChannel + "]的心跳包...");
        }

        clients.writeAndFlush(new TextWebSocketFrame(
                "[服务器在]" + LocalDateTime.now()
                        + "接收的消息：" + content
        ));

    }

    /**
     * 当客户端连接到服务端，获取客户端的channel，放到channelGroup中管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        System.out.println("客户端被移除，channelId为：" + channelId);
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
		clients.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        clients.remove(ctx.channel());
    }
}
