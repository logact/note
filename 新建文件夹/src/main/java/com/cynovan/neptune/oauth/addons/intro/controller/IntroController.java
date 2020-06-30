package com.cynovan.neptune.oauth.addons.intro.controller;

import com.cynovan.neptune.oauth.base.utils.HttpLib;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "intro")
public class IntroController {
    @GetMapping("")
    public String signIn(HttpServletRequest request, Model model) {
        model.addAllAttributes(HttpLib.pageAttributes(request));
        return "intro/intro";
    }
}
