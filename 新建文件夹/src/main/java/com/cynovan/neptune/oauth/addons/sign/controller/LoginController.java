package com.cynovan.neptune.oauth.addons.sign.controller;

import com.cynovan.neptune.oauth.base.utils.HttpLib;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String signIn(HttpServletRequest request, Model model) {
        model.addAllAttributes(HttpLib.pageAttributes(request));
        return "sign/signIn";
    }

    @GetMapping("/signUp")
    public String signUp(HttpServletRequest request,
                         @RequestParam(value = "error", required = false) String error,
                         Model model) {
        if (StringUtils.isNotEmpty(error)) {
            model.addAttribute("error", error);
        }
        return "sign/signUp";
    }
}
