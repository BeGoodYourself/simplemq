package com.github.BeYourself.broker;

import com.github.BeGoodYourself.bo.MessageSource;
import com.github.BeGoodYourself.bo.MessageType;
import com.github.BeGoodYourself.bo.ResponseMessage;
import com.github.BeGoodYourself.bo.header.ProducerAckMessage;
import com.github.BeGoodYourself.core.AckTaskQueue;
import com.github.BeGoodYourself.core.MessageSystemConfig;
import com.github.BeGoodYourself.core.SemaphoreCache;
import com.github.BeGoodYourself.netty.ChannelCache;
import com.github.BeGoodYourself.util.NettyUtil;
import io.netty.channel.Channel;

import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2016/9/8.
 */
public class AckPullMessageController implements Callable<Void>{
    private volatile boolean stoped = false;

    public void stop() {
        stoped = true;
    }

    public boolean isStoped() {
        return stoped;
    }

    @Override
    public Void call() {
        while (!stoped) {
            SemaphoreCache.acquire(MessageSystemConfig.AckTaskSemaphoreValue);
            ProducerAckMessage ack = AckTaskQueue.getAck();
            String requestId = ack.getAck();
            ack.setAck("");

            Channel channel = ChannelCache.findChannel(requestId);
            if (NettyUtil.validateChannel(channel)) {
                ResponseMessage response = new ResponseMessage();
                response.setMsgId(requestId);
                response.setMessageSource(MessageSource.SimpleMQBroker);
                response.setMessageType(MessageType.SimpleMQProducerAck);
                response.setHeader(ack);

                channel.writeAndFlush(response);
            }
        }
        return null;
    }
}
