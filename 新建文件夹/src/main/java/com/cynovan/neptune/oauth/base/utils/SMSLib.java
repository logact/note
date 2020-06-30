package com.cynovan.neptune.oauth.base.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by azure on 23/11/2016.
 */

public class SMSLib {

    private static final String DEF_CHATSET = "UTF-8";
    private static final int DEF_CONN_TIMEOUT = 30000;
    private static final int DEF_READ_TIMEOUT = 30000;
    private static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    private static final String APPKEY = "a957a427fdd0175934db83585f82fe39";
    private static final String JUHE_URL = "http://v.juhe.cn/sms/send";
    private static final String REG_TEM_ID = "23919";
    private static final String COMPANY_REJECT_TEM_ID = "50028";
    private static final String COMPANY_INVITE_TEM_ID = "59899";

    private static final Multimap<String, String> templateParams = ArrayListMultimap.create();

    static {
        templateParams.put(REG_TEM_ID, "#code#");
        templateParams.put(COMPANY_REJECT_TEM_ID, "#company#");
        templateParams.put(COMPANY_REJECT_TEM_ID, "#result#");
        templateParams.put(COMPANY_INVITE_TEM_ID, "#company#");
        templateParams.put(COMPANY_INVITE_TEM_ID, "#code#");
    }

    // http://v.juhe.cn/sms/send?mobile=手机号码&tpl_id=短信模板ID&tpl_value=%23code%23%3D654654&key=

    public static boolean sendValidateCode(String mobile, String code) {
        return send(mobile, REG_TEM_ID, code);
    }

    public static boolean sendCompanyApply(String mobile, String companyName, String result) {
        return send(mobile, COMPANY_REJECT_TEM_ID, new String[]{
                companyName, result
        });
    }

    public static boolean sendCompanyInvite(String mobile, String company, String code) {
        return send(mobile, COMPANY_INVITE_TEM_ID, new String[]{
                company, code
        });
    }

    public static boolean sendKickoutCompany(String mobile, String companyName) {
        return send(mobile, "50154", companyName);
    }

    // 发送短信
    private static boolean send(String mobile, String templateId, String... values) {
        String result = null;
        Map params = new HashMap();//请求参数
        params.put("mobile", mobile);//接收短信的手机号码
        params.put("tpl_id", templateId);//短信模板ID，请参考个人中心短信模板设置
        Map<String, Object> valueMap = Maps.newHashMap();
        List<String> keys = (List<String>) templateParams.get(templateId);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = values[i];
            valueMap.put(key, value);
        }
        params.put("tpl_value", urlencode(valueMap));
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "");//返回数据的格式,xml或json，默认json
        try {
            result = net(JUHE_URL, params, "GET");
            JsonNode object = JsonLib.parseJSON(result);
            if (JsonLib.getInteger(object, "error_code") == 0) {
                // 发送成功
//                System.out.println(object.get("result"));
                return true;
            } else {
                // 发送失败
//                System.out.println(object.get("error_code") + ":" + object.get("reason"));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    private static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    private static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


}
