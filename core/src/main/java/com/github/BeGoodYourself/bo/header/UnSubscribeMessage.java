package com.github.BeGoodYourself.bo.header;

import com.github.BeGoodYourself.bo.HeaderMessage;

/**
 * Created by Administrator on 2016/9/7.
 */
public class UnSubscribeMessage extends HeaderMessage{
    private String consumerId;

    public UnSubscribeMessage(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }
}
