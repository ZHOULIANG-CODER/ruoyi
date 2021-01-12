package com.ruoyi.framework.redis;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.ip.AddressUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.project.monitor.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 *  发送和存储消息任务
 */
@Component
public class SendAndStorageProcess{

    @Autowired
    private ThreadPoolTaskExecutor threadTaskExecutor;  //注入线程池

    /**
     * 多线程调用发送消息
     * @param message
     * @return
     */
    public JSONObject sendAndStorageMsg(Message message) {

        Future<JSONObject> future = threadTaskExecutor.submit(new Callable<JSONObject>() {  //采用带返回值的方式
            @Override
            public JSONObject call() throws Exception {

                //1.调用相对比较耗时的发消息方法
                String code = sendMessage(message);
                message.setUpdateTime(new Date());
                if ("200".equals(code)){    //发送成功
                    message.setStatusCode("4000");
                }else{  //发送失败
                    message.setStatusCode("4001");
                }

                //2.存储消息
                storageMessage(message);

                JSONObject result = new JSONObject();
                result.put("code", "200");
                result.put("msg", "发送消息成功！");
                return result;
            }
        });

        JSONObject jsonResult = new JSONObject();   //返回结果
        try{
            if (future.isDone()){   //线程调度结束时，才获取结果
                jsonResult = future.get();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonResult;      //消息发送与存储结果
    }

    /**
     * 调用接口发送消息
     * @param message
     * @return
     */
    private String sendMessage(Message message) {
        try{
            //TODO 这里写一些发消息的业务逻辑
            System.out.println(Thread.currentThread().getName() + "线程发送消息成功");
            return "200";   //发送消息结果状态码
        }catch (Exception e){
            System.out.println(Thread.currentThread().getName() + "线程发送消息失败" );
            e.printStackTrace();
        }
        return "500";   //发送消息结果状态码
    }

    /**
     * 存消息到数据库
     * @param message
     * @return
     */
    private void storageMessage(Message message) {
        try{
            message.getOperLog().setOperLocation(AddressUtils.getRealAddressByIP(message.getOperLog().getOperIp()));
            SpringUtils.getBean(ISysOperLogService.class).insertOperlog(message.getOperLog());
            //TODO 这里执行插入消息到数据操作
            System.out.println(Thread.currentThread().getName() + "线程插入消息到数据库");
        }catch (Exception e){
            System.out.println(Thread.currentThread().getName() + "线程插入消息到数据库失败" );
            e.printStackTrace();
        }
    }
}