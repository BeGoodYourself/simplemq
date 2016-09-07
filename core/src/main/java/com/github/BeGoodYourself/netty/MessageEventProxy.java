package com.github.BeGoodYourself.netty;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface MessageEventProxy {
    void beforeMessage(Object msg);

    void afterMessage(Object msg);
}
