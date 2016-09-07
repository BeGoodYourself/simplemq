package com.github.BeGoodYourself.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/9/7.
 */
public class ProtobufSerializeUtil {
    private static ConcurrentHashMap<Class, Schema> schemaConcurrentHashMap = new ConcurrentHashMap<>();
    private static Objenesis objenesis = new ObjenesisStd(true);
    private static <T> Schema<T> getSchema(Class<T> cls){
        Schema schema = schemaConcurrentHashMap.get(cls);
        if(schema == null){
            synchronized (ProtobufSerializeUtil.class){
                if(schema == null){
                    schema = RuntimeSchema.createFrom(cls);
                    schemaConcurrentHashMap.put(cls, schema);
                }
            }
        }
        return schema;
    }

    public static <T> T derialize(Class<T> cls, byte[] bytes){
        T t = objenesis.newInstance(cls);
        ProtobufIOUtil.mergeFrom(bytes,t, (Schema<T>)getSchema(cls));
        return t;
    }

    public static <T> byte[] serialize(T t){
        LinkedBuffer linkedBuffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try{
           return ProtobufIOUtil.toByteArray( t, (Schema<T>)getSchema(t.getClass()),linkedBuffer);
        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
}
