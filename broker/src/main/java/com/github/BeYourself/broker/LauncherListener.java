package com.github.BeYourself.broker;

import com.github.BeGoodYourself.core.CallBackInvoker;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * Created by Administrator on 2016/9/7.
 */
public class LauncherListener implements ChannelFutureListener{
    private CallBackInvoker<Object> invoke = null;

    public LauncherListener(CallBackInvoker<Object> invoke) {
        this.invoke = invoke;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            invoke.setReason(future.cause());
        }

    }
}
