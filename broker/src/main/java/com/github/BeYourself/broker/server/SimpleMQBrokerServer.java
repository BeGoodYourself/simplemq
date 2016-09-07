package com.github.BeYourself.broker.server;

import com.github.BeGoodYourself.core.NettyClustersConfig;
import com.github.BeYourself.broker.MessageBrokerHandler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Administrator on 2016/9/7.
 */
public class SimpleMQBrokerServer extends BrokerParallelServer{
    private ThreadFactory threadBossFactory = new ThreadFactoryBuilder()
            .setNameFormat("AvatarMQBroker[BossSelector]-%d")
            .setDaemon(true)
            .build();

    private ThreadFactory threadWorkerFactory = new ThreadFactoryBuilder()
            .setNameFormat("AvatarMQBroker[WorkerSelector]-%d")
            .setDaemon(true)
            .build();

    private int brokerServerPort = 0;
    private ServerBootstrap bootstrap;
    private MessageBrokerHandler handler;
    private SocketAddress serverIpAddr;
    private NettyClustersConfig nettyClustersConfig = new NettyClustersConfig();
    private DefaultEventExecutorGroup defaultEventExecutorGroup;
    private EventLoopGroup boss;
    private EventLoopGroup workers;
}
