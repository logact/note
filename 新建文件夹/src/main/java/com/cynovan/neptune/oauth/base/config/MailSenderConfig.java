package com.cynovan.neptune.oauth.base.config;

import com.cynovan.neptune.oauth.base.utils.StringLib;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by Aric on 2016/11/22.
 */
@Configuration
public class MailSenderConfig {

    @Value("${smtp}")
    private String smtpServer;

    @Value("${smtp_port}")
    private Integer smtpPort;

    @Value("${username}")
    private String smtpUsername;

    @Value("${password}")
    private String smtpPassword;

    @Value("${ssl}")
    private Boolean smtpSsl;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl impl = new JavaMailSenderImpl();
        impl.setHost(smtpServer);
        impl.setPort(smtpPort);
        impl.setUsername(smtpUsername);
        impl.setPassword(smtpPassword);
        impl.setDefaultEncoding(StringLib.UTF_8);

        if (smtpSsl == true) {
            Properties javaMailProperties = new Properties();
            javaMailProperties.setProperty("mail.smtp.auth", "true");
            javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
            javaMailProperties.setProperty("mail.smtps.ssl.trust", "*");
            javaMailProperties.setProperty("mail.smtps.ssl.checkserveridentity", "true");
            impl.setJavaMailProperties(javaMailProperties);
        }
        return impl;
    }
}
