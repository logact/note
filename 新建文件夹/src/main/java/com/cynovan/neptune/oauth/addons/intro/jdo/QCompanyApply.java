package com.cynovan.neptune.oauth.addons.intro.jdo;

import com.cynovan.neptune.oauth.base.database.BaseJDO;
import com.cynovan.neptune.oauth.base.utils.DBUtilsNoCompany;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;

/**
 * Created by Aric on 2017/4/26.
 */
public class QCompanyApply extends BaseJDO {
    public static final String collectionName = "companyApply";


    @Override
    public void createIndex() {
        DBUtilsNoCompany.createIndex(collectionName, DocumentLib.newDoc("company_id", -1));
    }

    public static final String user_id = "user_id";

    public static final String company_id = "company_id";
    public static final String user_name = "user_name";
    public static final String user_email = "user_email";
    public static final String user_mobile = "user_mobile";
    public static final String state = "state";
}
