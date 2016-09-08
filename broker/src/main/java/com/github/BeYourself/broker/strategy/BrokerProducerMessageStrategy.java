package com.github.BeYourself.broker.strategy;

import com.github.BeGoodYourself.bo.RequestMessage;
import com.github.BeGoodYourself.bo.ResponseMessage;
import com.github.BeGoodYourself.bo.header.TopicMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2016/9/7.
 */
public class BrokerProducerMessageStrategy implements BrokerStrategy{
    private ProducerMessageListener hookProducer;
    private ChannelHandlerContext channelHandler;

    public BrokerProducerMessageStrategy() {

    }

    public void messageDispatch(RequestMessage request, ResponseMessage response) {
        TopicMessage message = (TopicMessage) request.getHeader();
        hookProducer.hookProducerMessage(message, request.getMsgId(), channelHandler.channel());
    }

    public void setHookProducer(ProducerMessageListener hookProducer) {
        this.hookProducer = hookProducer;
    }

    public void setChannelHandler(ChannelHandlerContext channelHandler) {
        this.channelHandler = channelHandler;
    }

    public void setHookConsumer(ConsumerMessageListener hookConsumer) {

    }
}
