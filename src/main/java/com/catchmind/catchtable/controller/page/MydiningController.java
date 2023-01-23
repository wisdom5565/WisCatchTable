package com.catchmind.catchtable.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("mydining")
public class MydiningController {

    @GetMapping("/planned")
    public ModelAndView planned() {
        return new ModelAndView("mydining/planned");
    }
    @GetMapping("/done")
    public ModelAndView done() {
        return new ModelAndView("mydining/done");
    }
    @GetMapping("/cancel")
    public ModelAndView cancel() {
        return new ModelAndView("mydining/cancel");
    }

    @GetMapping("/reserve/detail")
    public ModelAndView detail() {
        return new ModelAndView("mydining/detail");
    }
    @GetMapping("/emptySlot")
    public ModelAndView emptySlot() {
        return new ModelAndView("mydining/emptySlot");
    }
    @GetMapping("/reserveOpen")
    public ModelAndView reserveOpen() {
        return new ModelAndView("mydining/reserveOpen");
    }

    @GetMapping("/notifications")
    public ModelAndView notifications() {
        return new ModelAndView("mydining/notifications");
    }
}
