package com.github.BeGoodYourself.netty;

import com.github.BeGoodYourself.bo.RequestMessage;
import com.github.BeGoodYourself.bo.ResponseMessage;
import com.github.BeGoodYourself.bo.header.ProducerAckMessage;
import com.github.BeGoodYourself.core.CallBackInvoker;
import com.github.BeGoodYourself.core.CallBackListener;
import com.github.BeGoodYourself.core.NotifyCallback;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MessageProcessor {
    private MessageConnectFactory factory = null;
    private MessageConnectPool pool = null;

    public MessageProcessor(String serverAddress) {
        MessageConnectPool.setServerAddress(serverAddress);
        pool = MessageConnectPool.getMessageConnectPoolInstance();
        this.factory = pool.borrow();
    }

    public void closeMessageConnectFactory() {
        pool.restore();
    }

    public MessageConnectFactory getMessageConnectFactory() {
        return factory;
    }

    public void sendAsynMessage(RequestMessage request, final NotifyCallback listener) {
        Channel channel = factory.getMessageChannel();
        if (channel == null) {
            return;
        }

        Map<String, CallBackInvoker<Object>> callBackMap = factory.getCallBackMap();

        CallBackInvoker<Object> invoker = new CallBackInvoker<Object>();
        callBackMap.put(request.getMsgId(), invoker);

        invoker.setRequestId(request.getMsgId());

        invoker.join(new CallBackListener<Object>() {
            public void onCallBack(Object t) {
                ResponseMessage response = (ResponseMessage) t;
                listener.onEvent((ProducerAckMessage) response.getHeader());

            }
        });

        ChannelFuture channelFuture = channel.writeAndFlush(request);
        channelFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    invoker.setReason(future.cause());
                }
            }
        });

    }

    public Object sendAsynMessage(RequestMessage request) {
        Channel channel = factory.getMessageChannel();

        if (channel == null) {
            return null;
        }

        Map<String, CallBackInvoker<Object>> callBackMap = factory.getCallBackMap();

        CallBackInvoker<Object> invoker = new CallBackInvoker<Object>();
        callBackMap.put(request.getMsgId(), invoker);
        invoker.setRequestId(request.getMsgId());

        ChannelFuture channelFuture = channel.writeAndFlush(request);
        channelFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    invoker.setReason(future.cause());
                }
            }
        });

        try {
            Object result = invoker.getMessageResult(factory.getTimeOut(), TimeUnit.MILLISECONDS);
            callBackMap.remove(request.getMsgId());
            return result;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendSyncMessage(RequestMessage request) {
        Channel channel = factory.getMessageChannel();

        if (channel == null) {
            return;
        }

        Map<String, CallBackInvoker<Object>> callBackMap = factory.getCallBackMap();

        CallBackInvoker<Object> invoker = new CallBackInvoker<Object>();
        callBackMap.put(request.getMsgId(), invoker);

        invoker.setRequestId(request.getMsgId());

        ChannelFuture channelFuture;
        try {
            channelFuture = channel.writeAndFlush(request).sync();
            channelFuture.addListener(new ChannelFutureListener() {

                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        invoker.setReason(future.cause());
                    }
                }
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
