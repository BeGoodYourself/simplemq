package com.github.BeGoodYourself.netty;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MessageConnectPoolableObjectFactory implements PooledObjectFactory<MessageConnectFactory>{
    private String serverAddress;
    private int sessionTimeOut = 3 * 1000;

    public MessageConnectPoolableObjectFactory(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public MessageConnectPoolableObjectFactory(String serverAddress, int sessionTimeOut) {
        this.serverAddress = serverAddress;
        this.sessionTimeOut = sessionTimeOut;
    }

    public PooledObject<MessageConnectFactory> makeObject() throws Exception {
        MessageConnectFactory factory = new MessageConnectFactory(serverAddress);
        return new DefaultPooledObject<>(factory);
    }

    public void destroyObject(PooledObject<MessageConnectFactory> obj) throws Exception {
             obj.getObject().close();
    }

    public boolean validateObject(PooledObject<MessageConnectFactory> obj) {
        return true;
    }

    public void activateObject(PooledObject<MessageConnectFactory> obj) throws Exception {

    }

    public void passivateObject(PooledObject<MessageConnectFactory> obj) throws Exception {
        MessageConnectFactory factory = (MessageConnectFactory) obj.getObject();
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }
}
