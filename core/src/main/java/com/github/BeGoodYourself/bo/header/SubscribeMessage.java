package com.github.BeGoodYourself.bo.header;

import com.github.BeGoodYourself.bo.HeaderMessage;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Administrator on 2016/9/7.
 */
public class SubscribeMessage extends HeaderMessage{
    private String clusterId;
    private String topic;
    private String consumerId;

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("clusterId", clusterId)
                .append("topic", topic)
                .append("consumerId", consumerId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SubscribeMessage)) return false;

        SubscribeMessage that = (SubscribeMessage) o;

        return new EqualsBuilder()
                .append(clusterId, that.clusterId)
                .append(topic, that.topic)
                .append(consumerId, that.consumerId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(clusterId)
                .append(topic)
                .append(consumerId)
                .toHashCode();
    }
}
