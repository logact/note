package com.cynovan.neptune.oauth.base.config.jms;

import com.cynovan.neptune.oauth.base.utils.DigestLib;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

/**
 * Created by Aric on 2016/11/22.
 */
@Configuration
public class JMSConfiguration {

    public static final String DeviceSubTopic = "VirtualTopic.devicesub";

    public static final String JanusSubTopic = "VirtualTopic.janussub";

    public static final String SYSTEM_TOKEN = DigestLib.md5Hex("SYS_TOKEN_NEPTUNE");

    @Value("${broker}")
    private String broker;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();

        ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
        prefetchPolicy.setAll(15);
        connectionFactory.setPrefetchPolicy(prefetchPolicy);

        /*消费端优化，确认收到消息使用批量确定，减少和MQ之间的IO操作*/
        connectionFactory.setOptimizeAcknowledge(true);
        connectionFactory.setOptimizeAcknowledgeTimeOut(3000);

        RedeliveryPolicy redeliveryPolicy = connectionFactory.getRedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(1);

        connectionFactory.setBrokerURL(broker);
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setUserName(SYSTEM_TOKEN);
        connectionFactory.setWatchTopicAdvisories(true);
        return connectionFactory;
    }

    @Bean
    public PooledConnectionFactory pooledConnectionFactory() {
        PooledConnectionFactory connectionFactory = new PooledConnectionFactory();
        connectionFactory.setConnectionFactory(activeMQConnectionFactory());
        return connectionFactory;
    }
    @Bean(name = "jmsTopicTemplate")
    public JmsTemplate jmsTopicTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(pooledConnectionFactory());
        /* 默认是 queue, 设置 true 为 pub/sub */
        template.setPubSubDomain(true);
        return template;
    }
}
