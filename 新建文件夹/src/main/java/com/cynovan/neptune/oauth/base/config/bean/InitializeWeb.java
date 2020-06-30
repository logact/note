package com.cynovan.neptune.oauth.base.config.bean;

import com.cynovan.neptune.oauth.base.arch.base.controller.BaseWeb;
import com.cynovan.neptune.oauth.base.context.SpringContext;
import com.cynovan.neptune.oauth.base.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Aric
 * @date 2016/11/15
 */
@Controller
@RequestMapping(value = "initialize")
public class InitializeWeb extends BaseWeb {

    private final static Long ONE_YEAR_IN_SECOND = 86400L * 365 * 10;

    /**
     * 10 年
     */
    public final static Long CACHE_EXPIRE_DATE = System.currentTimeMillis() + (ONE_YEAR_IN_SECOND * 1000 * 10);

    /**
     * 10 年
     */
    public final static Long CACHE_EXPIRE_SECOND = ONE_YEAR_IN_SECOND * 10;

    @Value("${debug}")
    private Boolean debug;

    @ResponseBody
    @RequestMapping(value = "template/{template_name}", produces = "text/html;text/plain")
    public String template(@PathVariable("template_name") String name, HttpServletResponse response) {
        setResponseHeaderContentType(response, "text/html;");
        String template = InitializeData.getTemplate(name);
        if (template == null) {
            template = "";
        }
        return template;
    }


    @ResponseBody
    @RequestMapping(value = "reload/{type}")
    public String reload(@PathVariable("type") String type) {
        InitializeService initializeService = SpringContext.getBean(InitializeService.class);
        initializeService.initializeTemplate();
        RedisUtils.delete("Oauth_InitializeData_Template");
        return "success";
    }

    private void setResponseHeaderContentType(HttpServletResponse response, String contentType) {
        if (debug == false) {
            response.setDateHeader("Expires", CACHE_EXPIRE_DATE);
            response.setHeader("Cache-Control", "max-age=" + CACHE_EXPIRE_SECOND);
        }
        response.setHeader("Content-Type", contentType);
        response.setCharacterEncoding("utf-8");
    }
}
