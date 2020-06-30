package com.cynovan.neptune.oauth.base.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FilenameUtil {

    public static void setFilenameHeader(HttpServletResponse response, String filename) throws UnsupportedEncodingException {
        filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"; filename*=utf-8''" + filename);
    }

}
