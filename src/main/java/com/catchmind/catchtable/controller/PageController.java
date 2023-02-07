package com.catchmind.catchtable.controller;

import com.catchmind.catchtable.domain.Profile;
import com.catchmind.catchtable.dto.network.request.ProfileRequest;
import com.catchmind.catchtable.service.ProfileLogicService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("")
public class PageController {

    private final PasswordEncoder passwordEncoder;
    private final ProfileLogicService profileLogicService;

    @GetMapping("")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping("join")
    public ModelAndView join() {
        return new ModelAndView("/join");
    }

    @PostMapping("/join")
    public String join(ProfileRequest request, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            return "join";
        }
        try{
            Profile profile = Profile.createMember(request, passwordEncoder);
            profileLogicService.saveMember(profile);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "join";
        }
        return "redirect:/";
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public boolean nickCheck(@RequestParam("prNick")String prNick){
        Optional<Profile> profile = profileLogicService.checkNick(prNick);
        if (profile.isEmpty()){
            return true;
        }else {
            return false;
        }
    }


    @GetMapping("/pending")
    public ModelAndView inquiry (){
        return new ModelAndView("/inquiry");
    }
}
