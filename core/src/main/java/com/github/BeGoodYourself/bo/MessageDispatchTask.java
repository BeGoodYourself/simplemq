package com.github.BeGoodYourself.bo;

import com.github.BeGoodYourself.bo.header.TopicMessage;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/8.
 */
public class MessageDispatchTask implements Serializable{
    private String clusters;

    private String topic;

    private TopicMessage message;

    public String getClusters() {
        return clusters;
    }

    public void setClusters(String clusters) {
        this.clusters = clusters;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public TopicMessage getMessage() {
        return message;
    }

    public void setMessage(TopicMessage message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MessageDispatchTask)) return false;

        MessageDispatchTask that = (MessageDispatchTask) o;

        return new EqualsBuilder()
                .append(clusters, that.clusters)
                .append(topic, that.topic)
                .append(message, that.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(clusters)
                .append(topic)
                .append(message)
                .toHashCode();
    }
}
