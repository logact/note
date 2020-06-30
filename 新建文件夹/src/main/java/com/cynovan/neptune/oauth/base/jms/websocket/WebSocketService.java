package com.cynovan.neptune.oauth.base.jms.websocket;

import com.cynovan.neptune.oauth.base.arch.base.controller.BaseService;
import com.cynovan.neptune.oauth.base.utils.DateUtils;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * @author Aric
 * @date 2016/12/27
 * 使用ActiveMQ的topic转发到Socket对应地址
 */
@Component
public class WebSocketService extends BaseService {

    @Autowired
    @Qualifier("jmsTopicTemplate")
    private JmsTemplate jmsTopicTemplate;

    private static final String websocketTopic = "wstopic";

    private void pushMessage(String topic, String data) {
        if (StringLib.startsWith(topic, "/")) {
            topic = StringLib.substring(topic, 1);
        }
        if (StringLib.contains(topic, "ws/")) {
            topic = StringLib.replace(topic, "ws/", "");
        }
        topic = StringLib.replace(topic, "//", "/");
        jmsTopicTemplate.convertAndSend(websocketTopic, data, new WSMessagePostProcessor(topic));
    }

    public void pushMessage(String topic, ObjectNode dataNode) {
        processTime(dataNode);
        pushMessage(topic, dataNode.toString());
    }

    public void pushMessage(String topic, Document dataNode) {
        processTime(dataNode);
        pushMessage(topic, dataNode.toJson());
    }

    private void processTime(Document dataNode) {
        if (dataNode.containsKey("time")) {
            Object timeValue = dataNode.get("time");
            if (timeValue instanceof Date) {
                dataNode.put("time", DateUtils.formatDateTime((Date) timeValue));
            }
        }
    }

    private void processTime(ObjectNode dataNode) {
        if (dataNode.has("time")) {
            Object timeValue = dataNode.get("time");
            if (timeValue instanceof Date) {
                dataNode.put("time", DateUtils.formatDateTime((Date) timeValue));
            }
        }
    }
}
