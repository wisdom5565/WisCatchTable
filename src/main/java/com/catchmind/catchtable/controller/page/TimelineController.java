package com.catchmind.catchtable.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("timeline")
public class TimelineController {

    @GetMapping("")
    public ModelAndView timeline() {
        return new ModelAndView("timeline/profile");
    }
    @GetMapping("/collection")
    public ModelAndView collection() {
        return new ModelAndView("timeline/profile-collection");
    }
    @GetMapping("/review")
    public ModelAndView review() {
        return new ModelAndView("timeline/profile-review");
    }
    @GetMapping("/follower")
    public ModelAndView follower() {
        return new ModelAndView("timeline/tabFeedFollower");
    }
}
