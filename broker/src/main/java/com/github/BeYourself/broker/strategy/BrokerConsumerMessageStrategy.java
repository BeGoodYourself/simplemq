package com.github.BeYourself.broker.strategy;

import com.github.BeGoodYourself.bo.RequestMessage;
import com.github.BeGoodYourself.bo.ResponseMessage;
import com.github.BeGoodYourself.core.CallBackInvoker;
import com.github.BeGoodYourself.core.ConsumerMessageListener;
import com.github.BeGoodYourself.core.ProducerMessageListener;
import com.github.BeYourself.broker.SendMessageLauncher;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2016/9/7.
 */
public class BrokerConsumerMessageStrategy implements BrokerStrategy{

    public void messageDispatch(RequestMessage request, ResponseMessage response) {
        String key = response.getMsgId();
        if (SendMessageLauncher.getInstance().trace(key)) {
            CallBackInvoker<Object> future = SendMessageLauncher.getInstance().detach(key);
            if (future == null) {
                return;
            } else {
                future.setMessageResult(request);
            }
        }
    }

    public void setHookProducer(ProducerMessageListener hookProducer) {

    }

    public void setHookConsumer(ConsumerMessageListener hookConsumer) {

    }

    public void setChannelHandler(ChannelHandlerContext channelHandler) {

    }
}
