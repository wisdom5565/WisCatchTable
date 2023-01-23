package com.catchmind.catchtable.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("reservation")
public class ReservationController {

    @GetMapping("")
    public ModelAndView resMain() {
        return new ModelAndView("reservation/reservation");
    }

    @GetMapping("/payment")
    public ModelAndView payment() {
        return new ModelAndView("reservation/payment");
    }
}
