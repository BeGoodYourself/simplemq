package com.github.BeYourself.broker;

import com.github.BeGoodYourself.bo.RemoteChannelData;
import com.github.BeGoodYourself.bo.SubscriptionData;
import com.github.BeGoodYourself.bo.header.SubscribeMessage;
import com.github.BeGoodYourself.core.ConsumerContext;

/**
 * Created by Administrator on 2016/9/7.
 */
public class ConsumerMessageHook implements ConsumerMessageListener{
    @Override
    public void hookConsumerMessage(SubscribeMessage request, RemoteChannelData channel) {

        System.out.println("receive subcript info groupid:" + request.getClusterId() + " topic:" + request.getTopic() + " clientId:" + channel.getClientId());

        SubscriptionData subscript = new SubscriptionData();

        subscript.setTopic(request.getTopic());
        channel.setSubcript(subscript);

        ConsumerContext.addClusters(request.getClusterId(), channel);
    }
}
