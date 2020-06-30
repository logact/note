package com.cynovan.neptune.oauth.base.config.auth.dto;

import com.cynovan.neptune.oauth.base.utils.DBUtils;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import org.bson.Document;

/**
 * Created by Aric on 2017/4/26.
 */
public class QUserInfo {

    public static final String collectionName = "userInfo";


    public static Document findByAccount(String account) {
        Document query = DocumentLib.newDoc();
        String field = "mobile";
        if (StringLib.contains(account, "@")) {
            field = "email";
        }
        query.put(field, account);

        return DBUtils.find(QUserInfo.collectionName, query);
    }
}
