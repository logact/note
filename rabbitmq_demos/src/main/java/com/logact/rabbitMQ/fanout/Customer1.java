package com.logact.rabbitMQ.fanout;

import com.logact.rabbitMQ.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author: logact
 * @date: Created in 2020/4/7 13:37
 * @description:
 */
public class Customer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel =connection.createChannel();
        // 通道绑定交换机
        channel.exchangeDeclare("logs", "fanout");
        // 临时队列
        String queueName=channel.queueDeclare().getQueue();
        //绑定临时队列
        channel.queueBind(queueName,"logs","");
        //
        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public  void  handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,byte[]body){
                System.out.println("消费者1："+new String(body));
            }
        });


    }
}
