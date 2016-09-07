package com.github.BeGoodYourself.bo.header;

import com.github.BeGoodYourself.bo.HeaderMessage;

/**
 * Created by Administrator on 2016/9/7.
 */
public class ProducerAckMessage extends HeaderMessage{
    private String ack;
    private int status;

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
