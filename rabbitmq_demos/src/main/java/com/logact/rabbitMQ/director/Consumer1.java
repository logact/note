package com.logact.rabbitMQ.director;

import checkers.oigj.quals.O;
import com.logact.rabbitMQ.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;


/**
 * @author: logact
 * @date: Created in 2020/4/7 19:36
 * @description:
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("logs_direct", "direct");
        String queue=channel.queueDeclare().getQueue();
        channel.queueBind(queue, "logs_direct", "direct");
        channel.basicConsume(queue, true, new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,byte[]data){
               System.out.println(new String(data));
           }
        });
    }
}
