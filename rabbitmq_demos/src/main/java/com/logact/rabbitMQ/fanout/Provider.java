package com.logact.rabbitMQ.fanout;

import com.logact.rabbitMQ.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * @author: logact
 * @date: Created in 2020/4/7 13:38
 * @description:
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        provide();
        System.out.println("fanout product...");
    }
    public static  void provide() throws IOException {
        Connection connection= ConnectionUtil.getConnection();
        Channel channel =connection.createChannel();
        channel.exchangeDeclare("logs", "fanout");
        String msg="fanout message "+ ZonedDateTime.now();
        //将消息发送给交换机
        //第一个参数是 交换机的名字
        //第二个参数是 交换机类型
        //第三个参数是 广播类型
        channel.basicPublish("logs", "", null,msg.getBytes());


    }

}
