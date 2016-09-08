package com.github.BeYourself.broker;

import com.github.BeGoodYourself.bo.MessageDispatchTask;
import com.github.BeGoodYourself.bo.MessageStatus;
import com.github.BeGoodYourself.bo.header.ProducerAckMessage;
import com.github.BeGoodYourself.bo.header.TopicMessage;
import com.github.BeGoodYourself.clusters.ConsumerClusters;
import com.github.BeGoodYourself.core.AckTaskQueue;
import com.github.BeGoodYourself.core.MessageSystemConfig;
import com.github.BeGoodYourself.core.MessageTaskQueue;
import com.github.BeGoodYourself.core.SemaphoreCache;
import com.google.common.base.Joiner;
import io.netty.channel.Channel;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.ClosureUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AnyPredicate;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/7.
 */
public class ProducerMessageHook implements ProducerMessageListener{
    private List<ConsumerClusters> clustersSet = new ArrayList<ConsumerClusters>();
    private List<ConsumerClusters> focusTopicGroup = null;

    private void filterByTopic(String topic) {
        Predicate focusAllPredicate = new Predicate() {
            public boolean evaluate(Object object) {
                ConsumerClusters clusters = (ConsumerClusters) object;
                return clusters.findSubscriptionData(topic) != null;
            }
        };

        AnyPredicate any = new AnyPredicate(new Predicate[]{focusAllPredicate});

        Closure joinClosure = new Closure() {
            public void execute(Object input) {
                if (input instanceof ConsumerClusters) {
                    ConsumerClusters clusters = (ConsumerClusters) input;
                    clustersSet.add(clusters);
                }
            }
        };

        Closure ignoreClosure = new Closure() {
            public void execute(Object input) {
            }
        };

        Closure ifClosure = ClosureUtils.ifClosure(any, joinClosure, ignoreClosure);

        CollectionUtils.forAllDo(focusTopicGroup, ifClosure);
    }

    private boolean checkClustersSet(TopicMessage msg, String requestId) {
        if (clustersSet.size() == 0) {
            System.out.println("AvatarMQ don't have match clusters!");
            ProducerAckMessage ack = new ProducerAckMessage();
            ack.setMsgId(msg.getMsgId());
            ack.setAck(requestId);
            ack.setStatus(MessageStatus.SUCCESS);
            AckTaskQueue.pushAck(ack);
            SemaphoreCache.release(MessageSystemConfig.AckTaskSemaphoreValue);
            return false;
        } else {
            return true;
        }
    }

    private void dispatchTask(TopicMessage msg, String topic) {
        List<MessageDispatchTask> tasks = new ArrayList<MessageDispatchTask>();

        for (int i = 0; i < clustersSet.size(); i++) {
            MessageDispatchTask task = new MessageDispatchTask();
            task.setClusters(clustersSet.get(i).getClustersId());
            task.setTopic(topic);
            task.setMessage(msg);
            tasks.add(task);

        }

        MessageTaskQueue.getInstance().pushTask(tasks);

        for (int i = 0; i < tasks.size(); i++) {
            //SemaphoreCache.release(MessageSystemConfig.NotifyTaskSemaphoreValue);
        }
    }

    private void taskAck(TopicMessage msg, String requestId) {
        try {
            Joiner joiner = Joiner.on(MessageSystemConfig.MessageDelimiter).skipNulls();
            String key = joiner.join(requestId, msg.getMsgId());
            //AckMessageCache.getAckMessageCache().appendMessage(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hookProducerMessage(TopicMessage msg, String requestId, Channel channel) {

        //ChannelCache.pushRequest(requestId, channel);

        String topic = msg.getTopic();

        //focusTopicGroup = ConsumerContext.selectByTopic(topic);

        filterByTopic(topic);

        if (checkClustersSet(msg, requestId)) {
            dispatchTask(msg, topic);
            taskAck(msg, requestId);
            //clustersSet.clear();
        } else {
            return;
        }
    }
}
