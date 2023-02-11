package com.catchmind.catchtable.controller;


import com.catchmind.catchtable.domain.Sns;
import com.catchmind.catchtable.dto.BistroSaveDto;
import com.catchmind.catchtable.dto.MyCollectionDto;
import com.catchmind.catchtable.dto.ProfileDto;
import com.catchmind.catchtable.dto.network.request.MyCollectionRequest;
import com.catchmind.catchtable.dto.network.request.ProfileRequest;
import com.catchmind.catchtable.dto.network.request.SnsRequest;
import com.catchmind.catchtable.dto.network.response.ReviewResponse;
import com.catchmind.catchtable.dto.network.response.TimeLineResponse;
import com.catchmind.catchtable.dto.security.CatchPrincipal;
import com.catchmind.catchtable.repository.BistroSaveRepository;
import com.catchmind.catchtable.repository.ProfileRepository;
import com.catchmind.catchtable.repository.SnsRepository;
import com.catchmind.catchtable.service.PaginationService;
import com.catchmind.catchtable.service.ProfileLogicService;
import com.catchmind.catchtable.service.TimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
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
    @Autowired
    private TimeLineService timeLineService;
    @Autowired
    private SnsRepository snsRepository;


    // 마이페이지 헤더
    public TimeLineResponse header(Long prIdx) {
        TimeLineResponse response = timeLineService.getHeader(prIdx);
        return response;
    }

    // 마이페이지 메인
    @GetMapping("")
    public ModelAndView myMain(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        if (catchPrincipal == null) {
            ModelAndView modelAndView = new ModelAndView("/login");
            return modelAndView;
        }
        boolean isSnsAddr = false;
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        for(int i=0; i < header.snsList().size(); i++){
            if(!header.snsList().get(i).snsAddr().isBlank()){
                isSnsAddr = false;
                break;
            }else{
                isSnsAddr = true;
            }
        }
        if(header.snsList().isEmpty()){
            isSnsAddr = true;
        }

        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ModelAndView modelAndView = new ModelAndView("/mypage/mypage_main");
        modelAndView.addObject("profile", profile);
        modelAndView.addObject("header", header);
        modelAndView.addObject("isSnsAddr", isSnsAddr);
        modelAndView.addObject("prIdx", prIdx);
        return modelAndView;
    }

    // 비밀번호 확인
    @PostMapping("/")
    public String collectionJoin(String prHp, String prUserpw) {
        boolean isTrue = profileLogicService.login(prHp, prUserpw);
        if (isTrue) {
            return "redirect:/mypage/modify";
        } else {
            return "redirect:/mypage";
        }
    }

    // 내 정보 수정 페이지
    @GetMapping("/modify")
    public ModelAndView myMainModify(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ModelAndView modelAndView = new ModelAndView("/mypage/mypage_main_modify");
        modelAndView.addObject("profile", profile);
//        if(profile.prBirth()!=null || profile.prBirth() != ""){
        String[] arr = profile.prBirth().split(",");
        modelAndView.addObject("header", header);
        modelAndView.addObject("birth0", arr[0]);
        modelAndView.addObject("birth1", arr[1]);
        modelAndView.addObject("birth2", arr[2]);
//        }
        return modelAndView;
    }

    // 내 정보 수정 기능
    @PostMapping("/modify")
    public String updateProfile(@AuthenticationPrincipal CatchPrincipal catchPrincipal, ProfileRequest request) {
        Long prIdx = catchPrincipal.prIdx();
        profileLogicService.updateProfile(prIdx, request.toDto());
        return "redirect:/mypage";
    }

    // sns추가 페이지
    @GetMapping("/newSNS")
    public ModelAndView SNS(@AuthenticationPrincipal CatchPrincipal catchPrincipal,
                            Model model) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);

        String insAddr;
        String twtAddr;
        String youtubeAddr;
        String blogAddr;

        for(int i=0;i<header.snsList().size();i++){
            if(header.snsList().get(i).snsType().equals("INSTAGRAM")){
                insAddr=header.snsList().get(i).snsAddr();
                model.addAttribute("insAddr",insAddr);
            }else if(header.snsList().get(i).snsType().equals("TWITTER")){
                twtAddr=header.snsList().get(i).snsAddr();
                model.addAttribute("twtAddr",twtAddr);
            }else if(header.snsList().get(i).snsType().equals("YOUTUBE")){
                youtubeAddr=header.snsList().get(i).snsAddr();
                model.addAttribute("youtubeAddr",youtubeAddr);
            }else if(header.snsList().get(i).snsType().equals("BLOG")){
                blogAddr=header.snsList().get(i).snsAddr();
                model.addAttribute("blogAddr",blogAddr);
            }
        }

        boolean isSnsAddr = false;
        for(int i=0; i < header.snsList().size(); i++){
            if(header.snsList().get(i).snsAddr().isBlank()){
                isSnsAddr = true;
            }
        }
        if(header.snsList().isEmpty()){
            isSnsAddr = true;
        }

        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        model.addAttribute("prIdx", prIdx);
        model.addAttribute("header", header);
        model.addAttribute("profile", profile);
        model.addAttribute("isSnsAddr", isSnsAddr);
        return new ModelAndView("/mypage/newSNS");
    }

    // sns 추가 및 업데이트
    @PostMapping("/newSNS")
    public String saveSNS(@AuthenticationPrincipal CatchPrincipal catchPrincipal,
                          SnsRequest request,
                          HttpServletResponse response
    ) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);

        String[] arr1;
        String[] arr2;
        System.out.println(request.snsAddr());
        arr1 = request.snsAddr().split(",",-1);
        arr2 = request.snsType().split(",");

        for(int i = 0; i<arr1.length; i++){
            Optional<Sns> sns= snsRepository.findByProfile_PrIdxAndSnsType(prIdx,arr2[i]);
            if(sns.isEmpty()){
                profileLogicService.saveSNS(request, prIdx, arr1[i], arr2[i]);
            }else{
                profileLogicService.snsUpdate(prIdx,arr2[i],arr1[i]);
            }
        }

        return "redirect:/mypage";
    }

    // 내 리뷰 보기
    @GetMapping("/review")
    public ModelAndView myReview(Model model, @AuthenticationPrincipal CatchPrincipal catchPrincipal,
                                 @PageableDefault(size = 10, sort = "revIdx", direction = Sort.Direction.DESC) Pageable pageable) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        Page<ReviewResponse> reviews = profileLogicService.getReview(prIdx, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);

        ModelAndView modelAndView = new ModelAndView("/mypage/myReview");
        model.addAttribute("reviews", reviews);
        model.addAttribute("prIdx", prIdx);
        model.addAttribute("paginationBarNumbers", barNumbers);
        modelAndView.addObject("profile", profile);
        modelAndView.addObject("header", header);
        return modelAndView;
    }

    // 내 컬렉션 보기
    @GetMapping("/collection")
    public ModelAndView myCollection(@AuthenticationPrincipal CatchPrincipal catchPrincipal, Model model) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        List<MyCollectionDto> MyCollections = profileLogicService.getColList(prIdx);
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        model.addAttribute("profile", profile);
        model.addAttribute("list", MyCollections);
        model.addAttribute("header", header);
        System.out.println(MyCollections);
        ModelAndView modelAndView = new ModelAndView("/mypage/mycollection");
        return modelAndView;
    }


    // 내 컬렉션 상세보기
    @GetMapping("/collection/detail/{colIdx}")
    public ModelAndView myCollectionDetail(@PathVariable Long colIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal, Model model) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        List<BistroSaveDto> bistroSaves = profileLogicService.getSaveList(colIdx);
        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        MyCollectionDto myCollection = profileLogicService.getMyCollectionElements(colIdx);
//        model.addAttribute("list",sav)
        ModelAndView modelAndView = new ModelAndView("/mypage/mycollectionDetail");
        modelAndView.addObject("profile", profile);
        modelAndView.addObject("header", header);
        modelAndView.addObject("list", bistroSaves);
        modelAndView.addObject("myCollection", myCollection);
        return modelAndView;
    }

    // 컬렉션에 저장된 식당 삭제
    @PostMapping("/collection/detail/delRes")
    @ResponseBody
    public String myCollectionDelRes(@RequestBody BistroSaveDto request) {
        Long saveIdx = request.saveIdx();
        profileLogicService.delCollectionSave(saveIdx);
        return "ok";
    }


    // 내 컬렉션 삭제
    @DeleteMapping("/collection/detail")
    @ResponseBody
    public String delMyCollection(@RequestBody MyCollectionRequest request) {
        Long colIdx = request.colIdx();
        profileLogicService.delMyCollection(colIdx);
        return "ok";
    }

    // 컬렉션 상세 수정페이지
    @GetMapping("/collection/detail/{colIdx}/modify")
    public ModelAndView myCollectionModify(@PathVariable Long colIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal, Model model) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        System.out.println(catchPrincipal.prIdx());
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        MyCollectionDto myCollection = profileLogicService.getMyCollectionElements(colIdx);
//        model.addAttribute("list",sav)
        ModelAndView modelAndView = new ModelAndView("/mypage/mycollection_modify");
        modelAndView.addObject("profile", profile);
        modelAndView.addObject("header", header);
        modelAndView.addObject("myCollection", myCollection);
        return modelAndView;
    }

    // 나의 컬렉션 수정
    @PostMapping("/collection/detail/modify")
    @ResponseBody
    public String modifyMyCollection(@RequestBody MyCollectionRequest request) {
        System.out.println(request.colLock());
        Long colIdx = request.colIdx();
        System.out.println("들어오나?");
        System.out.println("🥩" + request);
        MyCollectionDto myCollection = profileLogicService.getMyCollectionElements(colIdx);
        profileLogicService.updateMyCollection(colIdx, request.toDto());
//        model.addAttribute("list",sav)
        ModelAndView modelAndView = new ModelAndView("/mypage/mycollection_modify");
        return "ok";
    }


    // 컬렉션 만들기 페이지
    @GetMapping("/collection/new")
    public ModelAndView myCollectionNew(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        System.out.println("🥩🥩" + prIdx);
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        ModelAndView modelAndView = new ModelAndView("/mypage/new_mycollection");
        modelAndView.addObject("profile", profile);
        modelAndView.addObject("header", header);
        return modelAndView;
    }

    // 컬렉션 만들기 기능
    @PostMapping("/myCollection/new")
    @ResponseBody
    public String createCollection(@RequestBody MyCollectionRequest request) {
        System.out.println(request);
        profileLogicService.createCollection(request);
        return "ok";
    }

    // 컬렉션 레스토랑추가 > 저장된 식당 리스트
    @GetMapping("/collection/saveList/{prIdx}/{colIdx}")
    public ModelAndView addColSaveList(@PathVariable Long prIdx, Model model, @PathVariable Long colIdx) {
        List<BistroSaveDto> bistroSaves = profileLogicService.getList(prIdx);
        TimeLineResponse header = header(prIdx);
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        model.addAttribute("profile", profile);
        model.addAttribute("list", bistroSaves);
        model.addAttribute("colIdx", colIdx);
        model.addAttribute("header", header);
        System.out.println(bistroSaves);
        ModelAndView modelAndView = new ModelAndView("/mypage/myCollection_save_restaurant");
        return modelAndView;
    }


    //콜렉션에 식당 저장, 저장한 식당 테이블에 콜렉션 아이디 업데이트
    @PostMapping("/collection/saveRes")
    public String myCollectionSaveRes(@RequestParam Long colIdx, @RequestParam String bisNames) {
        System.out.println("🤍" + bisNames);
        System.out.println("💕" + colIdx);
        profileLogicService.updateMyCollectionSave(colIdx, bisNames);
        profileLogicService.updateBistroSave(colIdx, bisNames);
        return "redirect:/mypage/collection/detail/" + colIdx;
    }

    //  저장된 식당 리스트 보기
    @GetMapping("/saveList/{prIdx}")
    public ModelAndView myList(@PathVariable Long prIdx, Model model) {
        List<BistroSaveDto> bistroSaves = profileLogicService.getList(prIdx);
        TimeLineResponse header = header(prIdx);
        ProfileDto profile = profileLogicService.getProfileElements(prIdx);
        model.addAttribute("profile", profile);
        model.addAttribute("list", bistroSaves);
        model.addAttribute("header", header);
        System.out.println(bistroSaves);
        ModelAndView modelAndView = new ModelAndView("/mypage/save_restaurant");
        return modelAndView;
    }

    // 저장된 식당 삭제
    @DeleteMapping("/saveList")
    @ResponseBody
    public String delRes(@RequestBody BistroSaveDto request) {
        System.out.println("💕💕💕💕 !!  " + request.saveIdx());
        Long saveIdx = request.saveIdx();
        profileLogicService.delRes(saveIdx);
        return "ok";
    }

    // 회원 탈퇴
    @DeleteMapping("{prIdx}")
    public void delete(@PathVariable Long prIdx, HttpServletRequest request) {
        // 현재 세션 얻어와서 없애고
        HttpSession session = request.getSession(false);
        session.invalidate();
        // 시큐리티 인증정보 없애기
        SecurityContextHolder.getContext().setAuthentication(null);
        profileLogicService.delete(prIdx);
    }
}
