package com.github.BeGoodYourself.core;

import com.github.BeGoodYourself.bo.header.ProducerAckMessage;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface NotifyCallback {
    void onEvent(ProducerAckMessage result);
}
