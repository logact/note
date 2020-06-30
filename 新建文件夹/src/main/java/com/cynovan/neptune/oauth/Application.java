package com.cynovan.neptune.oauth;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.TimeZone;

@EnableWebSecurity
@EnableEurekaClient
@SpringCloudApplication
public class Application {

    public static void main(String[] args) throws Exception {
        TimeZone.setDefault(TimeZone.getDefault());
        Undertow.builder().setServerOption(UndertowOptions.NO_REQUEST_TIMEOUT, 60 * 1000);
        SpringApplication.run(Application.class, args);
    }
}
