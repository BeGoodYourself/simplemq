package com.github.BeYourself.broker.strategy;

import com.github.BeGoodYourself.bo.MessageType;
import com.github.BeGoodYourself.bo.RemoteChannelData;
import com.github.BeGoodYourself.bo.RequestMessage;
import com.github.BeGoodYourself.bo.ResponseMessage;
import com.github.BeGoodYourself.bo.header.SubscribeMessage;
import com.github.BeGoodYourself.core.ConsumerMessageListener;
import com.github.BeGoodYourself.core.ProducerMessageListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2016/9/7.
 */
public class BrokerSubscribeStrategy implements BrokerStrategy{
    private ConsumerMessageListener hookConsumer;
    private ChannelHandlerContext channelHandler;

    public BrokerSubscribeStrategy() {

    }

    public void messageDispatch(RequestMessage request, ResponseMessage response) {
        SubscribeMessage subscribe = (SubscribeMessage) request.getHeader();
        String clientKey = subscribe.getConsumerId();
        RemoteChannelData channel = new RemoteChannelData(channelHandler.channel(), clientKey);
        hookConsumer.hookConsumerMessage(subscribe, channel);
        response.setMessageType(MessageType.SimpleMQConsumerAck);
        channelHandler.writeAndFlush(response);
    }

    public void setHookConsumer(ConsumerMessageListener hookConsumer) {
        this.hookConsumer = hookConsumer;
    }

    public void setChannelHandler(ChannelHandlerContext channelHandler) {
        this.channelHandler = channelHandler;
    }

    public void setHookProducer(ProducerMessageListener hookProducer) {

    }
}
