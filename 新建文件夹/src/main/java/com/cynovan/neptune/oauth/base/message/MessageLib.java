package com.cynovan.neptune.oauth.base.message;

import com.cynovan.neptune.oauth.base.context.SpringContext;
import com.cynovan.neptune.oauth.base.jms.websocket.WebSocketService;
import com.cynovan.neptune.oauth.base.message.dto.MessageDto;
import com.cynovan.neptune.oauth.base.message.enums.MessageDtoLevel;
import com.cynovan.neptune.oauth.base.message.enums.MessageDtoType;
import com.cynovan.neptune.oauth.base.utils.DBUtilsNoCompany;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;
import com.cynovan.neptune.oauth.base.utils.JsonLib;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.bson.Document;

import java.util.Date;

public class MessageLib {

    public static void send(MessageDto messageDto) {
        JsonNode jsonNode = JsonLib.toJSON(messageDto);
        Document document = DocumentLib.parse(jsonNode.toString());
        // If level = alert, type = deviceStateChange, set expire_date.
        if (StringLib.equals(messageDto.getLevel(), MessageDtoLevel.alert.getValue())
                && StringLib.equals(messageDto.getType(), MessageDtoType.deviceState.getValue())) {
            document.append("expire_date", new Date());
        }
        DBUtilsNoCompany.insert(MessageDto.collectionName, document);
        WebSocketService webSocketService = SpringContext.getBean(WebSocketService.class);
        String ws = "message/" + messageDto.getCompany_id();
        webSocketService.pushMessage(ws, (ObjectNode) jsonNode);
    }
}
