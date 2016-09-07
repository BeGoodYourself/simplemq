package com.github.BeGoodYourself.bo;

/**
 * Created by Administrator on 2016/9/7.
 */
public class BusinessMessage extends AbstractMessage{
    protected HeaderMessage header;
    protected MessageSource messageSource;
    protected MessageType messageType;

    public HeaderMessage getHeader() {
        return header;
    }

    public void setHeader(HeaderMessage header) {
        this.header = header;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
