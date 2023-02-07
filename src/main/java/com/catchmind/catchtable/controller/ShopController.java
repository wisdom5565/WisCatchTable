package com.catchmind.catchtable.controller;

import com.catchmind.catchtable.domain.BistroInfo;
import com.catchmind.catchtable.dto.*;
import com.catchmind.catchtable.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("shop")
@RequiredArgsConstructor
public class ShopController {
    private final BistroDetailLogicService bistroDetailLogicService;
    private final BistroInfoLogicService bistroInfoLogicService;
    private final MenuLogicService menuLogicService;
    private final BisNoticeLogicService bisNoticeLogicService;
    private final ReviewLogicService reviewLogicService;
    private final PaginationService paginationService;
    private final FacilityLogicService facilityLogicService;

    @GetMapping("/review/{resaBisName}")
    public String shopReview(@PageableDefault(size=10, sort="bisIdx", direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam("page") Integer page,
                             @RequestParam("size") Integer size,
                             @RequestParam("sort") String sort,
                             ModelMap map,
                             @PathVariable String resaBisName) {
        PageRequest pageRequest=null;
        System.out.println("sort 값은? : " + sort);
        String filtername=null;
        if(sort.equals("regDate")){
            System.out.println("1");
            pageRequest = PageRequest.of(page,size,Sort.by(sort).descending());
            filtername="최근 등록순";
        }else if(sort.equals("revScoreGood")){
            System.out.println("2");
            pageRequest = PageRequest.of(page,size,Sort.by("revScore").descending());
            filtername="별점 높은순";
        }else if(sort.equals("revScoreBad")){
            System.out.println("3");
            pageRequest = PageRequest.of(page,size,Sort.by("revScore"));
            filtername="별점 낮은순";
        }

        List<ReviewDto> lists = reviewLogicService.reviewList(resaBisName);
        Page<ReviewDto> rlists = reviewLogicService.reviewList(resaBisName,pageRequest);
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(page, rlists.getTotalPages());
//        map.addAttribute("shopList", list);
        map.addAttribute("paginationBarNumbers",barNumbers);

        //평균 별점
        System.out.println(rlists);
        Double sum=0.0;
        Double avg=0.0;
        int cnt=0;
        for(ReviewDto review : lists){
            sum+=review.revScore();
            cnt+=1;
        }
        avg= sum/cnt;
        String average = String.format("%.1f", avg);
        map.addAttribute("reviews",rlists);
        map.addAttribute("avgpoint",average);
        map.addAttribute("revcnt",cnt);
        map.addAttribute("sort",sort);
        map.addAttribute("shopname",resaBisName);
        map.addAttribute("sort",sort);
        map.addAttribute("filtername",filtername);
        System.out.println("last");

        return "shop/shopReview";
    }

    @GetMapping("/menulist/{resaBisName}")
    public String menu(@PathVariable String resaBisName, ModelMap map) {
        System.out.println("inside");
        List<MenuDto> list = menuLogicService.menuList(resaBisName);
        map.addAttribute("menu" , list);
        map.addAttribute("resaBisName", resaBisName);
        System.out.println(list);
        return "shop/menuAllList";

    }

    @GetMapping("/list")
    public String list(@PageableDefault(size=10, sort="bisIdx", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam("page") Integer page,
                       @RequestParam("size") Integer size,
                       @RequestParam("sort") String sort,
                       ModelMap map) {
        PageRequest pageRequest=null;
        System.out.println("sort 값은? : " + sort);
        String filtername=null;
        if(sort.equals("regDate")){
            System.out.println("1");
            pageRequest = PageRequest.of(page,size,Sort.by(sort).descending());
            filtername="최근 등록순";
        }else if(sort.equals("revScoreGood")){
            System.out.println("2");
            pageRequest = PageRequest.of(page,size,Sort.by("revScore").descending());
            filtername="별점 높은순";
        }else if(sort.equals("revScoreBad")){
            System.out.println("3");
            pageRequest = PageRequest.of(page,size,Sort.by("revScore"));
            filtername="별점 낮은순";
        }
        System.out.println("pageable: "+ pageable);

        Page<BistroInfoDto> list = bistroInfoLogicService.shopList(pageRequest);
        List<BistroInfo> cntlist = bistroInfoLogicService.shopList();
        //List<ReviewDto> reviewList = reviewLogicService.reviewList();
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), list.getTotalPages());

        System.out.println(list);
        map.addAttribute("shopList", list);
        map.addAttribute("paginationBarNumbers",barNumbers);
        map.addAttribute("filtername",filtername);
        System.out.println("필터네임은 : @@@@@@@@"+filtername);

        // 총 레스토랑 수
        int cnt= cntlist.size();
        map.addAttribute("cnt", cnt);


        return "shop/list";
    }

    @GetMapping("/{resaBisName}")
    public String service(@PathVariable String resaBisName, ModelMap map) {
        BistroInfoDto list = bistroInfoLogicService.infoList(resaBisName);
        System.out.println(list);
        map.addAttribute("bisInfo", list);

        BistroDetailDto lists = bistroDetailLogicService.detailList(resaBisName);
        map.addAttribute("bisDetail" ,lists);

        List<BisNoticeDto> nlists = bisNoticeLogicService.noticeList(resaBisName);
        map.addAttribute("bisNotice" ,nlists);

        List<MenuDto> listss = menuLogicService.menuList(resaBisName);
        map.addAttribute("menu" , listss);

        List<FacilityDto> flist = facilityLogicService.facilityList(resaBisName);
        map.addAttribute("facility",flist);
        System.out.println("flist"+ flist);

        //식당 평균 별점
        List<ReviewDto> rlists = reviewLogicService.reviewList(resaBisName);
        System.out.println(rlists);
        Double sum=0.0;
        Double avg=0.0;
        int cnt=0;
        for(ReviewDto review : rlists){
            sum+=review.revScore();
            cnt+=1;
        }
        avg= sum/cnt;
        String average = String.format("%.1f", avg);
        map.addAttribute("reviews",rlists);
        map.addAttribute("avgpoint",average);
        map.addAttribute("revcnt",cnt);

        return "shop/shop";

    }




}
