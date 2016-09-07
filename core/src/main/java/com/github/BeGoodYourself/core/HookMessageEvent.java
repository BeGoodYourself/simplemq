package com.github.BeGoodYourself.core;

/**
 * Created by Administrator on 2016/9/7.
 */
public abstract class HookMessageEvent <T>{
    public void disconnect(T message) {
    }

    public T callBackMessage(T message) {
        return message;
    }
}
