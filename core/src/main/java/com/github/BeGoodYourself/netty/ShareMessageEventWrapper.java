package com.github.BeGoodYourself.netty;

import com.github.BeGoodYourself.core.HookMessageEvent;
import io.netty.channel.ChannelHandler;

/**
 * Created by Administrator on 2016/9/7.
 */
@ChannelHandler.Sharable
public class ShareMessageEventWrapper<T> extends MessageEventWrapper<T>{
    public ShareMessageEventWrapper() {
        super.setWrapper(this);
    }

    public ShareMessageEventWrapper(MessageProcessor processor) {
        super(processor);
        setWrapper(this);
    }

    public ShareMessageEventWrapper(MessageProcessor processor, HookMessageEvent<T> hook) {
        super(processor, hook);
        setWrapper(this);
    }
}
