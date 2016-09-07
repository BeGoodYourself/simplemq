package com.github.BeGoodYourself.bo;

/**
 * Created by Administrator on 2016/9/7.
 */
public enum  MessageSource {
    SimpleMQConsumer(1),
    SimpleMQBroker(2),
    SimpleMQProducer(3);

    private int source;

    private MessageSource(int source) {
        this.source = source;
    }

    public int getSource() {
        return source;
    }
}
