package com.github.BeGoodYourself.core;

/**
 * Created by Administrator on 2016/9/7.
 */
public class NettyClustersConfig {
    public static int getWorkerThreads() {
        return Runtime.getRuntime().availableProcessors() * 2;
    }

    public int getClientSocketSndBufSize() {
        return MessageSystemConfig.SocketSndbufSize;
    }

    public int getClientSocketRcvBufSize() {
        return MessageSystemConfig.SocketRcvbufSize;
    }
}
