package com.cynovan.neptune.oauth.addons.intro.jdo;

import com.cynovan.neptune.oauth.base.database.BaseJDO;

/**
 * Created by Aric on 2017/4/26.
 */
public class QCompanyInvite extends BaseJDO {

    public static final String collectionName = "companyInvite";

    public static final String to = "to";
    public static final String from = "from";
    public static final String from_id = "from_id";
    public static final String company_id = "company_id";
    public static final String company_name = "company_name";

    public static final String email = "email";
    public static final String mobile = "mobile";
    public static final String code = "code";
    public static final String url = "url";

    public static final String security = "security";

    public static final String hashkey = "hashkey";
    public static final String expired = "expired";


}
