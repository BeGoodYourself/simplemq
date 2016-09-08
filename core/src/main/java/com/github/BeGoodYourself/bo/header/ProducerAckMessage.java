package com.github.BeGoodYourself.bo.header;

import com.github.BeGoodYourself.bo.HeaderMessage;
import com.github.BeGoodYourself.bo.MessageStatus;

/**
 * Created by Administrator on 2016/9/7.
 */
public class ProducerAckMessage extends HeaderMessage{
    private String ack;
    private MessageStatus status;

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
}
