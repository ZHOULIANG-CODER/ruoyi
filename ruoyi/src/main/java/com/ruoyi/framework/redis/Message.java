package com.ruoyi.framework.redis;

import com.ruoyi.project.monitor.domain.SysOperLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private String id;          //消息id
    private String personNo;    //发送人的学工号（可指定多个，以逗号隔开，不超过1000个）
    private String title;       //消息标题
    private String content;     //消息内容
    private String type;        //消息类型，system（系统消息）、sms（短信消息）
    private Date createTime;    //创建时间
    private Date updateTime;    //更新时间
    private String statusCode;  //消息发送结果状态码（4000表示成功，4001表示失败）
    private SysOperLog operLog;
    
}