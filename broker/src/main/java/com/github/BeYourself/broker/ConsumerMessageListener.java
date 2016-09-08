package com.github.BeYourself.broker;

import com.github.BeGoodYourself.bo.RemoteChannelData;
import com.github.BeGoodYourself.bo.header.SubscribeMessage;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface ConsumerMessageListener {
    void hookConsumerMessage(SubscribeMessage msg, RemoteChannelData channel);
}
