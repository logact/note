package com.cynovan.neptune.oauth.addons.intro.jdo;

import com.cynovan.neptune.oauth.base.database.BaseJDO;
import com.cynovan.neptune.oauth.base.utils.DBUtilsNoCompany;
import com.cynovan.neptune.oauth.base.utils.DocumentLib;

/**
 * Created by Aric on 2017/4/28.
 */
public class QDeviceAnalyzeData extends BaseJDO {

    public static final String collectionName = "deviceAnalyzeData";

    @Override
    public void createIndex() {
        DBUtilsNoCompany.createIndex(collectionName, DocumentLib.newDoc("uuid", 1).append("time", -1),true);
    }

    public static final String uuid = "uuid";
    public static final String action = "action";
    public static final String data_id = "data_id";
    public static final String data = "data";
    public static final String time = "time";
    public static final String create_time = "create_time";
    public static final String time_format = "time_format";
}
