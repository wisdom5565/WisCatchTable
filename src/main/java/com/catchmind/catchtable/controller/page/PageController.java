package com.catchmind.catchtable.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class PageController {

    @GetMapping("")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }

    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping("join")
    public ModelAndView join() {
        return new ModelAndView("/join");
    }
}
