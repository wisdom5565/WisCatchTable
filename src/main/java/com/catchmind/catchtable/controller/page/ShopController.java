package com.catchmind.catchtable.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("shop")
public class ShopController {

    @GetMapping("")
    public ModelAndView shop() {
        return new ModelAndView("shop/shop");
    }
    @GetMapping("/review")
    public ModelAndView shopReview() {
        return new ModelAndView("shop/shopReview");
    }
    @GetMapping("/menulist")
    public ModelAndView menuList() {
        return new ModelAndView("shop/menuList");
    }

    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("shop/list");
    }
}
