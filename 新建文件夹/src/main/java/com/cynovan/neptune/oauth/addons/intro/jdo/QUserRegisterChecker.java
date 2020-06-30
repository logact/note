package com.cynovan.neptune.oauth.addons.intro.jdo;

import com.cynovan.neptune.oauth.base.database.BaseJDO;
import com.cynovan.neptune.oauth.base.utils.DBUtilsNoCompany;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;

/**
 * Created by Aric on 2017/4/26.
 */
public class QUserRegisterChecker extends BaseJDO {

    public static final String collectionName = "userRegisterChecker";

    public static final String code1 = "code1";
    public static final String code2 = "code2";
    public static final String user_id = "user_id";
    public static final String username = "username";
    public static final String email = "email";
    public static final String mobile = "mobile";
    public static final String create_date = "create_date";
    public static final String captcha = "captcha";
    public static final String expired = "expired";

    @Override
    public void createIndex() {
        DBUtilsNoCompany.createIndex(collectionName, DocumentLib.newDoc("code1", -1));
    }
}
