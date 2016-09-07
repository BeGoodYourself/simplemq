package com.github.BeGoodYourself.netty;

import com.github.BeGoodYourself.core.HookMessageEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MessageEventWrapper<T> extends ChannelInboundHandlerAdapter implements MessageEventHandler, MessageEventProxy{

    final public static String proxyMappedName = "handleMessage";
    protected MessageProcessor processor;
    protected Throwable cause;
    protected HookMessageEvent<T> hook;
    protected MessageConnectFactory factory;
    private MessageEventWrapper<T> wrapper;

    public MessageEventWrapper() {
    }

    public MessageEventWrapper(MessageProcessor processor) {
        this(processor, null);
    }

    public MessageEventWrapper(MessageProcessor processor, HookMessageEvent<T> hook) {
        this.processor = processor;
        this.hook = hook;
        this.factory = processor.getMessageConnectFactory();
    }

    @Override
    public void handleMessage(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void beforeMessage(Object msg) {

    }

    @Override
    public void afterMessage(Object msg) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        this.cause = cause;
        cause.printStackTrace();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        ProxyFactory weaver = new ProxyFactory(wrapper);
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName(MessageEventWrapper.proxyMappedName);
        advisor.setAdvice(new MessageEventAdvisor(wrapper, msg));
        weaver.addAdvisor(advisor);

        MessageEventHandler proxyObject = (MessageEventHandler) weaver.getProxy();
        proxyObject.handleMessage(ctx, msg);
    }

    public void setWrapper(MessageEventWrapper<T> wrapper) {
        this.wrapper = wrapper;
    }

    public Throwable getCause() {
        return cause;
    }
}
