package com.catchmind.catchtable.controller;

import com.catchmind.catchtable.domain.Profile;
import com.catchmind.catchtable.dto.PendingDto;
import com.catchmind.catchtable.dto.network.request.ProfileRequest;
import com.catchmind.catchtable.dto.network.response.TimeLineResponse;
import com.catchmind.catchtable.service.PendingService;
import com.catchmind.catchtable.service.ProfileLogicService;
import com.catchmind.catchtable.service.TimeLineService;
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
    private final TimeLineService timeLineService;
    private final PendingService pendingService;

    // 마이페이지 헤더
    public TimeLineResponse header(Long prIdx) {
        TimeLineResponse response = timeLineService.getHeader(prIdx);
        return response;
    }
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

    // 입점문의 페이지
    @GetMapping("pending")
    public ModelAndView inquiry (){
        return new ModelAndView("/inquiry");
    }

    // 입점문의 등록
    @PostMapping("/pending")
    public String inquiry (PendingDto pendingDto){
        System.out.println(pendingDto);
        pendingService.createResAdmin(pendingDto);
        return "redirect:/";
    }

    @GetMapping("/findPassword")
    public ModelAndView findPw () {
        return new ModelAndView("/findPw");
    }
    @PostMapping("/findPassword")
    @ResponseBody
    public Optional<Profile> findPassword (@RequestParam("prHp")String prHp,
                                           @RequestParam("prName")String prName) {
        Optional<Profile> profile = profileLogicService.findPw(prHp,prName);
        return profile;
    }
    @GetMapping("/resetPassword/{prHp}")
    public ModelAndView resetPw (@PathVariable String prHp, Model model){
        model.addAttribute("prHp",prHp);
        return new ModelAndView("/resetPassword");
    }
    @PostMapping("/resetPassword")
    public String resetPassword (@RequestParam("prHp")String prHp,
                                 ProfileRequest request){
        System.out.println("🐓🐓🐓🐓🐓🐓🐓🐓🐓  "+request.prHp());
        profileLogicService.updatePassword(prHp, request.toDto());
        return "/login";
    }


}
