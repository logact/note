package com.cynovan.neptune.oauth.base.jms.websocket;

import com.cynovan.neptune.oauth.base.utils.StringLib;
import org.springframework.jms.core.MessagePostProcessor;

import javax.jms.JMSException;

public class WSMessagePostProcessor implements MessagePostProcessor {

    private String topic = null;

    public WSMessagePostProcessor(String _topic) {
        this.topic = _topic;
    }

    @Override
    public javax.jms.Message postProcessMessage(javax.jms.Message message) throws JMSException {
        if (StringLib.isNotEmpty(topic)) {
            message.setStringProperty("topic", topic);
        }
        return message;
    }
}
