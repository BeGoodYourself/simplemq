package com.github.BeYourself.broker;

import com.github.BeGoodYourself.bo.ResponseMessage;
import com.github.BeGoodYourself.core.CallBackInvoker;
import com.github.BeGoodYourself.core.MessageSystemConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/9/7.
 */
public class SendMessageLauncher {
    private long timeout = MessageSystemConfig.MessageTimeOutValue;

    public Map<String, CallBackInvoker<Object>> invokeMap = new ConcurrentSkipListMap<>();

    private SendMessageLauncher() {

    }

    private static SendMessageLauncher resource;

    public static SendMessageLauncher getInstance() {
        if (resource == null) {
            synchronized (SendMessageLauncher.class) {
                if (resource == null) {
                    resource = new SendMessageLauncher();
                }
            }
        }
        return resource;
    }

    public Object launcher(Channel channel, ResponseMessage response) {
        if (channel != null) {
            CallBackInvoker<Object> invoke = new CallBackInvoker<>();
            invokeMap.put(response.getMsgId(), invoke);
            invoke.setRequestId(response.getMsgId());
            ChannelFuture channelFuture = channel.writeAndFlush(response);
            channelFuture.addListener(new LauncherListener(invoke));
            try {
                Object result = invoke.getMessageResult(timeout, TimeUnit.MILLISECONDS);
                return result;
            } catch (RuntimeException e) {
                throw e;
            } finally {
                invokeMap.remove(response.getMsgId());
            }
        } else {
            return null;
        }
    }

    public boolean trace(String key) {
        return invokeMap.containsKey(key);
    }

    public CallBackInvoker<Object> detach(String key) {
        if (invokeMap.containsKey(key)) {
            return invokeMap.remove(key);
        } else {
            return null;
        }
    }
}
