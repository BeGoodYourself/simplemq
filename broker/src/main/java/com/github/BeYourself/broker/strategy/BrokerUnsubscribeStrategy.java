package com.github.BeYourself.broker.strategy;

import com.github.BeGoodYourself.bo.RequestMessage;
import com.github.BeGoodYourself.bo.ResponseMessage;
import com.github.BeGoodYourself.bo.header.UnSubscribeMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2016/9/7.
 */
public class BrokerUnsubscribeStrategy implements BrokerStrategy{
    public void messageDispatch(RequestMessage request, ResponseMessage response) {
        UnSubscribeMessage msgUnSubscribe = (UnSubscribeMessage) request.getHeader();
        //ConsumerContext.unLoad(msgUnSubscribe.getConsumerId());
    }

    public void setHookProducer(ProducerMessageListener hookProducer) {

    }

    public void setHookConsumer(ConsumerMessageListener hookConsumer) {

    }

    public void setChannelHandler(ChannelHandlerContext channelHandler) {

    }
}
