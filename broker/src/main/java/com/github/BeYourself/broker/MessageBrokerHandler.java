package com.github.BeYourself.broker;

import com.github.BeGoodYourself.bo.MessageSource;
import com.github.BeGoodYourself.bo.RequestMessage;
import com.github.BeGoodYourself.bo.ResponseMessage;
import com.github.BeGoodYourself.core.ConsumerMessageListener;
import com.github.BeGoodYourself.core.ProducerMessageListener;
import com.github.BeGoodYourself.netty.ShareMessageEventWrapper;
import com.github.BeYourself.broker.strategy.BrokerStrategyContext;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MessageBrokerHandler extends ShareMessageEventWrapper<Object>{
    private AtomicReference<ProducerMessageListener> hookProducer;
    private AtomicReference<ConsumerMessageListener> hookConsumer;
    private AtomicReference<RequestMessage> message = new AtomicReference<RequestMessage>();

    public MessageBrokerHandler() {
        super.setWrapper(this);
    }

    public MessageBrokerHandler buildProducerHook(ProducerMessageListener hookProducer) {
        this.hookProducer = new AtomicReference<ProducerMessageListener>(hookProducer);
        return this;
    }

    public MessageBrokerHandler buildConsumerHook(ConsumerMessageListener hookConsumer) {
        this.hookConsumer = new AtomicReference<ConsumerMessageListener>(hookConsumer);
        return this;
    }

    public void handleMessage(ChannelHandlerContext ctx, Object msg) {
        RequestMessage request = message.get();
        ResponseMessage response = new ResponseMessage();
        response.setMsgId(request.getMsgId());
        response.setMessageSource(MessageSource.SimpleMQBroker);

        BrokerStrategyContext strategy = new BrokerStrategyContext(request, response, ctx);
        strategy.setHookConsumer(hookConsumer.get());
        strategy.setHookProducer(hookProducer.get());
        strategy.invoke();
    }

    public void beforeMessage(Object msg) {
        message.set((RequestMessage) msg);
    }
}
