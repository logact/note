package com.cynovan.neptune.oauth.addons.intro.jdo;


import com.cynovan.neptune.oauth.base.database.BaseJDO;
import com.cynovan.neptune.oauth.base.utils.DigestLib;
import com.cynovan.neptune.oauth.base.utils.StringLib;

/**
 * Created by Aric on 2017/4/26.
 */
public class QCompany extends BaseJDO {

    public static final String collectionName = "company";

    public static final String name = "name";
    public static final String user_id = "user_id";
    public static final String email = "email";
    public static final String mobile = "mobile";
    public static final String type = "type";
    public static final String deviceGroup = "deviceGroup";
    public static final String device_control_pwd = "device_control_pwd";
    public static final String token = "token";
    public static final String device_amount = "device_amount";
    public static final String developer = "developer";

    public static final String storage_used = "storage_used";
    public static final String data_storage = "data_storage";

    private static final String salt = "_neptune";

    public static String encodePWD(String pwd) {
        pwd = new StringBuilder().append(pwd).append(salt).toString();
        pwd = DigestLib.md5Hex(pwd);
        return pwd;
    }

    public static boolean validatePwd(String pwd, String encode) {
        return StringLib.equals(encode, encodePWD(pwd));
    }

}
