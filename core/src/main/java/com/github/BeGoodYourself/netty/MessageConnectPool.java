package com.github.BeGoodYourself.netty;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MessageConnectPool extends GenericObjectPool<MessageConnectFactory>{
    private static MessageConnectPool pool = null;
    private static Properties messageConnectConfigProperties = null;
    private static String configPropertiesString = "com/newlandframework/avatarmq/netty/avatarmq.messageconnect.properties";
    private static String serverAddress = "";

    public static MessageConnectPool getMessageConnectPoolInstance() {
        if (pool == null) {
            synchronized (MessageConnectPool.class) {
                if (pool == null) {
                    try {
                        messageConnectConfigProperties = new Properties();

                        InputStream inputStream = MessageConnectPool.class.getClassLoader()
                                .getResourceAsStream(configPropertiesString);

                        messageConnectConfigProperties.load(inputStream);
                        inputStream.close();

                        //this.serverAddress = serverAddress;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    GenericObjectPoolConfig config = new GenericObjectPoolConfig();
                    //config.setMaxActive(maxActive);
                    config.setMaxIdle(Integer.parseInt(messageConnectConfigProperties.getProperty("maxIdle")));
                    config.setMinIdle(Integer.parseInt(messageConnectConfigProperties.getProperty("minIdle")));
                    //config.setMaxWait(maxWait);
                    config.setTestOnBorrow(false);
                    config.setTestOnReturn(false);
                    config.setTimeBetweenEvictionRunsMillis(10 * 1000);
                    config.setNumTestsPerEvictionRun(Integer.parseInt(messageConnectConfigProperties.getProperty("maxIdle")));
                    config.setMinEvictableIdleTimeMillis(30 * 60 * 1000);
                    config.setTestWhileIdle(true);
                    pool = new MessageConnectPool(new MessageConnectPoolableObjectFactory(serverAddress,
                            Integer.parseInt(messageConnectConfigProperties.getProperty("sessionTimeOut"))), config);
                    //pool.serverAddress = serverAddress;
                }
            }
        }
        return pool;
    }

    public MessageConnectPool(PooledObjectFactory<MessageConnectFactory> factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

    /**
    public MessageConnectPool() {
        try {
            messageConnectConfigProperties = new Properties();

            InputStream inputStream = MessageConnectPool.class.getClassLoader()
                    .getResourceAsStream(configPropertiesString);

            messageConnectConfigProperties.load(inputStream);
            inputStream.close();

            this.serverAddress = serverAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }

        int maxActive = Integer.parseInt(messageConnectConfigProperties.getProperty("maxActive"));
        int minIdle = Integer.parseInt(messageConnectConfigProperties.getProperty("minIdle"));
        int maxIdle = Integer.parseInt(messageConnectConfigProperties.getProperty("maxIdle"));
        int maxWait = Integer.parseInt(messageConnectConfigProperties.getProperty("maxWait"));
        int sessionTimeOut = Integer.parseInt(messageConnectConfigProperties.getProperty("sessionTimeOut"));

        System.out.printf("MessageConnectPool[maxActive=%d,minIdle=%d,maxIdle=%d,maxWait=%d,sessionTimeOut=%d]\n", maxActive, minIdle, maxIdle, maxWait, sessionTimeOut);

        this.setMaxActive(maxActive);
        this.setMaxIdle(maxIdle);
        this.setMinIdle(minIdle);
        this.setMaxWait(maxWait);
        this.setTestOnBorrow(false);
        this.setTestOnReturn(false);
        this.setTimeBetweenEvictionRunsMillis(10 * 1000);
        this.setNumTestsPerEvictionRun(maxActive + maxIdle);
        this.setMinEvictableIdleTimeMillis(30 * 60 * 1000);
        this.setTestWhileIdle(true);

        this(new MessageConnectPoolableObjectFactory(serverAddress, sessionTimeOut));
    }
*/


    public MessageConnectFactory borrow() {
        assert pool != null;
        try {
            return (MessageConnectFactory) pool.borrowObject();
        } catch (Exception e) {
            System.out.printf("get message connection throw the error from message connection pool, error message is %s\n",
                    e.getMessage());
        }
        return null;
    }

    public void restore() {
        assert pool != null;
        try {
            pool.close();
        } catch (Exception e) {
            System.out.printf("throw the error from close message connection pool, error message is %s\n",
                    e.getMessage());
        }
    }

    public static String getServerAddress() {
        return serverAddress;
    }

    public static void setServerAddress(String ipAddress) {
        serverAddress = ipAddress;
    }
}
