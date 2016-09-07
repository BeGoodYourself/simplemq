package com.github.BeGoodYourself.netty;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MessageEventAdvisor implements MethodInterceptor{
    private MessageEventProxy proxy;
    private Object msg;

    public MessageEventAdvisor(MessageEventProxy proxy, Object msg) {
        this.proxy = proxy;
        this.msg = msg;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        proxy.beforeMessage(msg);
        Object obj = invocation.proceed();
        proxy.afterMessage(msg);
        return obj;
    }
}
