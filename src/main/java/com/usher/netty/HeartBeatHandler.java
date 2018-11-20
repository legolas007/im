package com.usher.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author: Usher
 * @Description:
 * 检测channel的心跳
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("Read idle");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("Write idle");
            } else if (event.state() == IdleState.ALL_IDLE) {
                System.out.println("channel关闭前，users的数量为：" + ChatHandler.clients.size());

                Channel channel = ctx.channel();
                // 关闭无用的channel，以防资源浪费
                channel.close();

                System.out.println("channel关闭后，users的数量为：" + ChatHandler.clients.size());
            }
        }
    }
}
