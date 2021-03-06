package com.github.BeYourself.broker.strategy;

import com.github.BeGoodYourself.bo.MessageSource;
import com.github.BeGoodYourself.bo.RequestMessage;
import com.github.BeGoodYourself.bo.ResponseMessage;
import com.github.BeYourself.broker.ConsumerMessageListener;
import com.github.BeYourself.broker.ProducerMessageListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.EnumMap;

/**
 * Created by Administrator on 2016/9/7.
 */
public class BrokerStrategyContext {

    public static enum BrokerStrategyType{
        SimpleMQProducerMessageStrategy,
        SimpleMQConsumerMessageStrategy,
        SimpleMQSubscribeStrategy,
        SimpleMQUnsubscribeStrategy
    }
    
    private RequestMessage request;
    private ResponseMessage response;
    private ChannelHandlerContext channelHandler;
    private ProducerMessageListener hookProducer;
    private ConsumerMessageListener hookConsumer;
    private BrokerStrategy strategy;

    //private static Map strategyMap = TypedMap.decorate(new HashMap(), BrokerStrategyType.class, BrokerStrategy.class);
    private static EnumMap<BrokerStrategyType, BrokerStrategy> strategyMap = new EnumMap<>(BrokerStrategyType.class);
    
    static {
        strategyMap.put(BrokerStrategyType.SimpleMQProducerMessageStrategy, new BrokerProducerMessageStrategy());
        strategyMap.put(BrokerStrategyType.SimpleMQConsumerMessageStrategy, new BrokerConsumerMessageStrategy());
        strategyMap.put(BrokerStrategyType.SimpleMQSubscribeStrategy, new BrokerSubscribeStrategy());
        strategyMap.put(BrokerStrategyType.SimpleMQUnsubscribeStrategy, new BrokerUnsubscribeStrategy());
    }

    public BrokerStrategyContext(RequestMessage request, ResponseMessage response, ChannelHandlerContext channelHandler) {
        this.request = request;
        this.response = response;
        this.channelHandler = channelHandler;
    }

    public void setHookProducer(ProducerMessageListener hookProducer) {
        this.hookProducer = hookProducer;
    }

    public void setHookConsumer(ConsumerMessageListener hookConsumer) {
        this.hookConsumer = hookConsumer;
    }

    public void invoke() {
        switch (request.getMessageType()) {
            case SimpleMQMessage:
                strategy = strategyMap.get(request.getMessageSource() == MessageSource.SimpleMQProducer ?
                        BrokerStrategyType.SimpleMQProducerMessageStrategy : BrokerStrategyType.SimpleMQConsumerMessageStrategy);
                break;
            case SimpleMQSubscribe:
                strategy = strategyMap.get(BrokerStrategyType.SimpleMQSubscribeStrategy);
                break;
            case SimpleMQUnsubscribe:
                strategy = strategyMap.get(BrokerStrategyType.SimpleMQUnsubscribeStrategy);
                break;
            default:
                break;
        }

        strategy.setChannelHandler(channelHandler);
        strategy.setHookConsumer(hookConsumer);
        strategy.setHookProducer(hookProducer);
        strategy.messageDispatch(request, response);
    }
}
