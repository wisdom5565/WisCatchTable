package com.catchmind.catchtable.controller;


import com.catchmind.catchtable.domain.BistroSave;
import com.catchmind.catchtable.domain.Profile;
import com.catchmind.catchtable.dto.BistroSaveDto;
import com.catchmind.catchtable.dto.ProfileDto;
import com.catchmind.catchtable.dto.security.CatchPrincipal;
import com.catchmind.catchtable.repository.BistroSaveRepository;
import com.catchmind.catchtable.repository.ProfileRepository;
//import com.catchmind.catchtable.service.MypageLogicService;
import com.catchmind.catchtable.service.PaginationService;
import com.catchmind.catchtable.service.ProfileLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("mypage")
public class MypageController {


    @Autowired
    private ProfileLogicService profileLogicService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PaginationService paginationService;
    @Autowired
    private BistroSaveRepository bistroSaveRepository;

    @GetMapping("")
    public ModelAndView myMain(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        if(catchPrincipal == null){
            ModelAndView modelAndView = new ModelAndView("/login");
            return modelAndView;
        }
        Long prIdx = catchPrincipal.prIdx();
        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ModelAndView modelAndView = new ModelAndView("/mypage/mypage_main");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }

    @GetMapping("/modify")
    public ModelAndView myMainModify(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ModelAndView modelAndView = new ModelAndView("/mypage/mypage_main_modify");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }
    @GetMapping("/point")
    public ModelAndView myPoint(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ModelAndView modelAndView = new ModelAndView("/mypage/point");
        modelAndView.addObject("profile",profile);
        return modelAndView;
//        return new ModelAndView("/mypage/point");
    }

    @GetMapping("/review")
    public ModelAndView myReview(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ModelAndView modelAndView = new ModelAndView("/mypage/myReview");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }

    @GetMapping("/collection")
    public ModelAndView myCollection(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ModelAndView modelAndView = new ModelAndView("/mypage/mycollection");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }

    @GetMapping("/collection/detail")
    public ModelAndView myCollectionDetail(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ModelAndView modelAndView = new ModelAndView("/mypage/mycollectionDetail");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }

    @GetMapping("/collection/new")
    public ModelAndView myCollectionNew(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ModelAndView modelAndView = new ModelAndView("/mypage/new_mycollection");
        modelAndView.addObject("profile",profile);
        return modelAndView;
    }
    @GetMapping("/saveList/{prIdx}")
    public ModelAndView myList(@PathVariable Long prIdx, Model model) {
        List<BistroSaveDto> bistroSaves = profileLogicService.getList(prIdx);
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        model.addAttribute("profile", profile);
        model.addAttribute("list", bistroSaves);
        System.out.println(bistroSaves);
        ModelAndView modelAndView = new ModelAndView("/mypage/save_restaurant");
        return modelAndView;
    }


}
