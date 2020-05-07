package com.logact.rabbitMQ.day1;

import com.logact.rabbitMQ.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeoutException;

/**
 * @author: logact
 * @date: Created in 2020/4/7 11:14
 * @description:直接连接模式
 */

public class DirectCon {

}


class Provider {
    public static void main(String[] args) {
        try {
            testSendMessage();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void testSendMessage() throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();
        //获取连接通道
        Channel channel = connection.createChannel();
        //通道绑定消息队列
        //参数1：队列的名称 如果队列不存在则自动创
//            参数2：队列受否持久化，但是消息不会持久化 如果重启的话就会丢失消息
//            参数3: 是否独占队列（当前队列只允许当前的连接可用）
//            参数4: autoDelete :是否在消息完成后自动删除队列
//            参数5： 额外参数
        channel.queueDeclare("hello", false, false, false, null);

        //发布消息
        //参数1：交换机名称
//            参数2：队列名称
//            参数3: 传递消息的额外设置 可以设置消息持久化
//            参数4： 消息的具体内容

        channel.basicPublish("", "hello", null, "hello rabbitmq".getBytes());
        System.out.println("public successfully");
        channel.close();
        connection.close();
    }

}


class consumer {
    public static void main(String[] args) {
        try {
            test();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test() throws IOException {

        Connection connection = null;
        //创建连接对象
        connection = ConnectionUtil.getConnection();
        //获取连接通道
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", false, false, false, null);
        //设置每一次都只取一个消息通过这样结合将消息自动确认关掉就能实现能者多劳的模式
        channel.basicQos(1);
        //参数 1：消费那个队列的消息
        //参数2 :开始消息的自动确认机制 最好关掉防止消息丢失
        //参数3：
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override//回调方法
            //参数 :标签 信封
            //最后一个参数：消息队列中取出的消息
            //主线程消亡了这个线程依然在活动
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(ZonedDateTime.now().toString() + new String(body));
                //手动确认，
                // 参数1：确认队列中哪个具体的消息。
                // 参数2 ：是否开启多个消息同时确认。
                channel.basicAck(envelope.getDeliveryTag(), false);
            }

        });
//            如果这样的方式就会在处理的线程还没有开始处理主线程就已经关闭了
//            channel.close();
//            connection.close();
    }
}

