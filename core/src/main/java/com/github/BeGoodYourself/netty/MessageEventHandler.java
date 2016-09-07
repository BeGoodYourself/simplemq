package com.github.BeGoodYourself.netty;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface MessageEventHandler {
    void handleMessage(ChannelHandlerContext ctx, Object msg);
}
