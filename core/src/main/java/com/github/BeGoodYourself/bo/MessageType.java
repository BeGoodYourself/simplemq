package com.github.BeGoodYourself.bo;

/**
 * Created by Administrator on 2016/9/7.
 */
public enum MessageType {
    SimpleMQSubscribe(1),
    SimpleMQUnsubscribe(2),
    SimpleMQMessage(3),
    SimpleMQProducerAck(4),
    SimpleMQConsumerAck(5);

    private int messageType;

    private MessageType(int messageType) {
        this.messageType = messageType;
    }

    int getMessageType() {
        return messageType;
    }
}
