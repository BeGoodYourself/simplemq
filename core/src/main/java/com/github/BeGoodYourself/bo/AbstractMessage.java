package com.github.BeGoodYourself.bo;

/**
 * Created by Administrator on 2016/9/7.
 */
public abstract class AbstractMessage implements Message{

    protected String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
