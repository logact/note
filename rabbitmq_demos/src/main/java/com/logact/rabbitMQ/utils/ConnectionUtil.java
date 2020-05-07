package com.logact.rabbitMQ.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: logact
 * @date: Created in 2020/4/7 12:24
 * @description:
 */
public class ConnectionUtil {
    public static Connection getConnection() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("47.96.230.151");
        connectionFactory.setPort(5672);
        //设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("my_vhost");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        try {
            return connectionFactory.newConnection();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println("创建connection 失败");
            e.printStackTrace();
        }

        return null;
    }
}
