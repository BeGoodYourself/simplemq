package com.github.BeGoodYourself.core;

import com.github.BeGoodYourself.bo.header.ProducerAckMessage;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2016/9/8.
 */
public class AckTaskQueue {
    private static ConcurrentLinkedQueue<ProducerAckMessage> ackQueue = new ConcurrentLinkedQueue<>();

    public static boolean pushAck(ProducerAckMessage ack) {
        return ackQueue.offer(ack);
    }

    public static boolean pushAck(List<ProducerAckMessage> acks) {
        boolean flag = false;
        for (ProducerAckMessage ack : acks) {
            flag = ackQueue.offer(ack);
        }
        return flag;
    }

    public static ProducerAckMessage getAck() {
        return ackQueue.poll();
    }
}
