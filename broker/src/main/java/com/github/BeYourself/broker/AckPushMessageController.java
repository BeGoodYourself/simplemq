package com.github.BeYourself.broker;

import com.github.BeGoodYourself.core.AckMessageCache;
import com.github.BeGoodYourself.core.MessageSystemConfig;

import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2016/9/8.
 */
public class AckPushMessageController implements Callable<Void>{
    private volatile boolean stoped = false;

    @Override
    public Void call() {
        AckMessageCache ref = AckMessageCache.getAckMessageCache();
        int timeout = MessageSystemConfig.AckMessageControllerTimeOutValue;
        while (!stoped) {
            if (ref.hold(timeout)) {
                ref.commit();
            }
        }
        return null;
    }

    public void stop() {
        stoped = true;
    }

    public boolean isStoped() {
        return stoped;
    }
}
