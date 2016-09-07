package com.github.BeYourself.broker.server;

import com.github.BeGoodYourself.core.NettyClustersConfig;
import com.github.BeYourself.broker.SendMessageController;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/9/7.
 */
public class BrokerParallelServer implements RemotingServer{

    protected int parallel = NettyClustersConfig.getWorkerThreads();
    protected ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(parallel));
    protected ExecutorCompletionService<Void> executorCompletionService;


    @Override
    public void start() {
        executor.shutdown();
    }

    @Override
    public void init() {
        executorCompletionService = new ExecutorCompletionService<Void>(executor);
    }

    @Override
    public void shutdown() {
        for (int i =0 ; i < parallel; i++){
            executorCompletionService.submit(new SendMessageController());
        }
    }
}
