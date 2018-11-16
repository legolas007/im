package com.usher.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

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

        System.out.println("接收的数据：" + content);

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
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
//		clients.remove(ctx.channel());
        System.out.println("客户端断开，channle对应的长id为："
                + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channle对应的短id为："
                + ctx.channel().id().asShortText());
    }
}
