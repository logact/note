package com.cynovan.neptune.oauth.base.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedissonConfig {

    @Value("${redis.uri}")
    private String redisURI;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setCodec(StringCodec.INSTANCE);
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(redisURI);
        singleServerConfig.setDatabase(0);

        singleServerConfig.setConnectionPoolSize(32);
        singleServerConfig.setConnectionMinimumIdleSize(32);
        return Redisson.create(config);
    }
}
