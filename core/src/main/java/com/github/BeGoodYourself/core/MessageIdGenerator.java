package com.github.BeGoodYourself.core;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by Administrator on 2016/9/7.
 */
public class MessageIdGenerator {

    public static enum GeneratorStrategy{
        StrategyUUID,
        StrategyRandomDigital
    }


    private GeneratorStrategy generatorStrategy;
    private final SecureRandom secureRandom = new SecureRandom();

    private static MessageIdGenerator instance;

    public static void register(GeneratorStrategy generatorStrategy){
        if(instance == null){
            synchronized (MessageIdGenerator.class){
                if(instance == null){
                    instance = new MessageIdGenerator();
                    instance.generatorStrategy = generatorStrategy;
                }
            }
        }
    }
    public static MessageIdGenerator getInstance(){
        return instance;
    }

    public String generateId(){
        switch (generatorStrategy){
            case StrategyUUID:
                return UUID.randomUUID().toString();
            case StrategyRandomDigital:
                return new BigInteger(130, secureRandom).toString(16);
        }
        return "";
    }

}
