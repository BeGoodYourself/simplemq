package com.github.BeYourself.broker.strategy;

import com.github.BeGoodYourself.bo.RequestMessage;
import com.github.BeGoodYourself.bo.ResponseMessage;
import com.github.BeYourself.broker.ConsumerMessageListener;
import com.github.BeYourself.broker.ProducerMessageListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface BrokerStrategy {
    void messageDispatch(RequestMessage request, ResponseMessage response);

    void setHookProducer(ProducerMessageListener hookProducer);

    void setHookConsumer(ConsumerMessageListener hookConsumer);

    void setChannelHandler(ChannelHandlerContext channelHandler);
}
