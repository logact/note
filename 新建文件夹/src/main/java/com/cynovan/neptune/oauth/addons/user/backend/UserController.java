package com.cynovan.neptune.oauth.addons.user.backend;

import com.cynovan.neptune.oauth.base.utils.HttpLib;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(value = "user")
@Controller
public class UserController {

    @RequestMapping(value = "info")
    public String info(HttpServletRequest request, Model model) {
        model.addAllAttributes(HttpLib.pageAttributes(request));
        return "user/userInfo";
    }
}
