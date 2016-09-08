package com.github.BeYourself.broker;

import com.github.BeGoodYourself.bo.header.TopicMessage;
import io.netty.channel.Channel;


/**
 * Created by Administrator on 2016/9/7.
 */
public interface ProducerMessageListener {
    void hookProducerMessage(TopicMessage msg, String requestId, Channel channel);
}
