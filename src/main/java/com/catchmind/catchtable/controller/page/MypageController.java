package com.catchmind.catchtable.controller.page;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("mypage")
public class MypageController {

    @GetMapping("")
    public ModelAndView myMain() {
        return new ModelAndView("/mypage/mypage_main");
    }

    @GetMapping("/modify")
    public ModelAndView myMainModify() {
        return new ModelAndView("/mypage/mypage_main_modify");
    }

    @GetMapping("/review")
    public ModelAndView myReview() {
        return new ModelAndView("/mypage/myReview");
    }

    @GetMapping("/collection")
    public ModelAndView myCollection() {
        return new ModelAndView("/mypage/mycollaction");
    }

    @GetMapping("/collection/detail")
    public ModelAndView myCollectionDetail() {
        return new ModelAndView("/mypage/mycollactionDetail");
    }

    @GetMapping("/collection/new")
    public ModelAndView myCollectionNew() {
        return new ModelAndView("/mypage/new_mycollaction");
    }

    @GetMapping("/saveList")
    public ModelAndView myList() {
        return new ModelAndView("/mypage/save_restaurant");
    }

}
