package com.ruoyi.framework.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消息接收侦听器
 */
@Component
public class Receiver{

    @Autowired
    private SendAndStorageProcess sendAndStorageProcess;

    private AtomicInteger counter = new AtomicInteger();    //消息计数器

    /**
     * 接收系统消息，开启异步监听
     * @param message
     */
    @Async
    public void systemMessage(String message){
        int counter = this.counter.incrementAndGet();
        System.out.println("接收到第" + counter + "条消息！！频道为：system，消息内容为======：");
        //将消息内容字符串转化为对象
        Message messageObject = JSONObject.parseObject(message, Message.class);
        System.out.println(messageObject.getContent());

        //TODO 开启多线程调用发送并处理消息
        JSONObject result = sendAndStorageProcess.sendAndStorageMsg(messageObject);
    }

    /**
     * 接收短信消息，开启异步监听
     * @param message
     */
    @Async
    public void smsMessage(String message){
        int counter = this.counter.incrementAndGet();
        System.out.println("接收到第" + counter + "条消息！！频道为：sms，消息内容为======：");
        //将消息内容字符串转化为对象
        Message messageObject = JSONObject.parseObject(message, Message.class);
        System.out.println(messageObject.getContent());

        //TODO 开启多线程调用发送
        JSONObject result = sendAndStorageProcess.sendAndStorageMsg(messageObject);
    }

    /**
     * 接收log消息，开启异步监听
     * @param message
     */
    @Async
    public void logMessage(String message){
        int counter = this.counter.incrementAndGet();
        System.out.println("接收到第" + counter + "条消息！！频道为：log，消息内容为======：");
        //将消息内容字符串转化为对象
        Message messageObject = JSONObject.parseObject(message, Message.class);
        //TODO 开启多线程调用发送
        JSONObject result = sendAndStorageProcess.sendAndStorageMsg(messageObject);
    }

}