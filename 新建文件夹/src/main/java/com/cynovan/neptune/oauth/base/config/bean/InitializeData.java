package com.cynovan.neptune.oauth.base.config.bean;

import com.cynovan.neptune.oauth.base.arch.jdo.QTemplate;
import com.cynovan.neptune.oauth.base.context.SpringContext;
import com.cynovan.neptune.oauth.base.utils.DBUtilsNoCompany;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;
import com.cynovan.neptune.oauth.base.utils.JsonLib;
import com.cynovan.neptune.oauth.base.utils.RedisUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import org.bson.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by Aric on 2016/11/9.
 */
public class InitializeData {

    public static void removeAllCache() {
        RedisUtils.delete("InitializeData_Menu");
        RedisUtils.delete("InitializeData_MenuMap");
        RedisUtils.delete("Oauth_InitializeData_Template");
        RedisUtils.delete("InitializeData_Security");
    }

    private static void loadTemplateSecurity() {
        if (!RedisUtils.has("InitializeData_Security")) {
            InitializeService initializeService = SpringContext.getBean(InitializeService.class);
            String securityStr = initializeService.getTemplateSecurity();
            RedisUtils.set("InitializeData_Security", securityStr);
        }
    }

    public static Document getTemplateSecurity(String role) {
        loadTemplateSecurity();
        JsonNode node = RedisUtils.getJSON("InitializeData_Security");
        JsonNode roleNode = node.get("data").get(role);
        return DocumentLib.parse(JsonLib.toString(roleNode));
    }

    private static void loadTemplate() {
        String cacheKey = "Oauth_InitializeData_Template";
        if (!RedisUtils.has(cacheKey)) {
            List<Document> list = DBUtilsNoCompany.list(QTemplate.collectionName, null);
            Map<String, String> map = Maps.newHashMap();
            list.stream().forEach(item -> {
                map.put(DocumentLib.getString(item, QTemplate.name), DocumentLib.getString(item, QTemplate.template));
            });

            map.forEach((key, value) -> {
                RedisUtils.set(cacheKey, key, value);
            });
        }
    }

    public static String getTemplate(String name) {
        loadTemplate();
        return RedisUtils.get("Oauth_InitializeData_Template", name);
    }

}
