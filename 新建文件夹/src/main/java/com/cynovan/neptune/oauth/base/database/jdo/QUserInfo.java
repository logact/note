package com.cynovan.neptune.oauth.base.database.jdo;

import com.cynovan.neptune.oauth.base.utils.DBUtilsNoCompany;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import com.mongodb.client.model.Filters;
import org.bson.Document;

/**
 * Created by Aric on 2017/4/26.
 */
public class QUserInfo {

    public static final String collectionName = "userInfo";

    public static final String name = "name";
    public static final String token = "token";
    public static final String mobile = "mobile";
    public static final String create_date = "create_date";
    public static final String validate = "validate";
    public static final String email = "email";
    public static final String password = "password";
    public static final String image_id = "image_id";
    public static final String company_id = "company_id";
    public static final String deviceControlPolicy = "deviceControlPolicy";
    public static final String security = "security";

    public static Document findByAccount(String account) {
        Document query = DocumentLib.newDoc();
        String field = mobile;
        if (StringLib.contains(account, "@")) {
            field = email;
        }
        query.put(field, account);

        return DBUtilsNoCompany.find(QUserInfo.collectionName, query);
    }

    public static Document findByToken(String token) {
        return DBUtilsNoCompany.find(QUserInfo.collectionName, Filters.eq("token", token));
    }

    public static void updateUserToken(String id, String token) {
        DBUtilsNoCompany.updateOne(QUserInfo.collectionName, Filters.eq("_id", id), DocumentLib.new$Set("token", token));
    }

    public static void updateUserCompanyId(String user_id, String company_id) {
        DBUtilsNoCompany.updateOne(QUserInfo.collectionName,
                Filters.eq("id", user_id),
                DocumentLib.new$Set("company_id", company_id));
    }
}
