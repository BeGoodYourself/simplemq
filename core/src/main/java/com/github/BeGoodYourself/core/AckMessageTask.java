package com.github.BeGoodYourself.core;

import com.github.BeGoodYourself.bo.MessageStatus;
import com.github.BeGoodYourself.bo.header.ProducerAckMessage;
import com.google.common.base.Splitter;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2016/9/8.
 */
public class AckMessageTask implements Callable<Long>{
    CyclicBarrier barrier = null;
    String[] messages = null;
    private final AtomicLong count = new AtomicLong(0);

    public AckMessageTask(CyclicBarrier barrier, String[] messages) {
        this.barrier = barrier;
        this.messages = messages;
    }

    @Override
    public Long call() throws Exception {
        for (int i = 0; i < messages.length; i++) {
            boolean error = false;
            ProducerAckMessage ack = new ProducerAckMessage();
            Object[] msg = Splitter.on(MessageSystemConfig.MessageDelimiter).trimResults().splitToList(messages[i]).toArray();
            if (msg.length == 2) {
                ack.setAck((String) msg[0]);
                ack.setMsgId((String) msg[1]);

                if (error) {
                    ack.setStatus(MessageStatus.FAIL);
                } else {
                    ack.setStatus(MessageStatus.SUCCESS);
                    count.incrementAndGet();
                }

                AckTaskQueue.pushAck(ack);
                SemaphoreCache.release(MessageSystemConfig.AckTaskSemaphoreValue);
            }
        }

        barrier.await();
        return count.get();
    }
}
