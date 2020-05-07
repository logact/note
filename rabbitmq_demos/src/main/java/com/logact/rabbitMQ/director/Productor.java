package com.logact.rabbitMQ.director;

import com.logact.rabbitMQ.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author: logact
 * @date: Created in 2020/4/7 19:32
 * @description:
 */
public class Productor {
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel= connection.createChannel();
        channel.exchangeDeclare("logs_direct", "direct");
        String msg="there is direct product";
        channel.basicPublish("logs_direct", "direct", null, msg.getBytes());

    }

}
