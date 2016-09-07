package com.github.BeGoodYourself.bo.header;

import com.github.BeGoodYourself.bo.HeaderMessage;

/**
 * Created by Administrator on 2016/9/7.
 */
public class TopicMessage extends HeaderMessage{
    private String topic;
    private byte[] body;
    private long timeStamp;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
