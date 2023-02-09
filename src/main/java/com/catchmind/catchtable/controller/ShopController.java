package com.catchmind.catchtable.controller;

import com.catchmind.catchtable.domain.BistroInfo;
import com.catchmind.catchtable.domain.Photo;
import com.catchmind.catchtable.dto.*;
import com.catchmind.catchtable.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    private final PhotoLogicService photoLogicService;

    @GetMapping("/review/{resaBisName}")
    public String shopReview(@PageableDefault(size=10, sort="regDate", direction = Sort.Direction.DESC) Pageable pageable,
                             @RequestParam(value = "page",required = false) Integer page,
                             @RequestParam(value = "size",required = false) Integer size,
                             @RequestParam(value = "sort",required = false) String sort,
                             ModelMap map,
                             @PathVariable String resaBisName) {
        PageRequest pageRequest=null;

        String filtername=null;
        Page<ReviewDto> rlists=null;
        try{
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
            rlists = reviewLogicService.reviewList(resaBisName,pageRequest);
        }catch (Exception e){
            rlists = reviewLogicService.reviewList(resaBisName,pageable);
        }

        List<ReviewDto> lists = reviewLogicService.reviewList(resaBisName);

        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), rlists.getTotalPages());
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
        if (sort != null) {
            map.addAttribute("sort",sort);
        }

        map.addAttribute("shopname",resaBisName);
        map.addAttribute("filtername",filtername);
        System.out.println("last");

        //그래프
        Long score5=0L;
        Long score4=0L;
        Long score3=0L;
        Long score2=0L;
        Long score1=0L;
        Long scoreSum=0L;

        for(ReviewDto reviewDto: lists){
            if ((int)Math.round(reviewDto.revScore())==5) {
                score5 += 1L;
            } else if ((int)Math.round(reviewDto.revScore())==4) {
                score4 += 1L;
            } else if ((int)Math.round(reviewDto.revScore())==3) {
                score3 += 1L;
            } else if ((int)Math.round(reviewDto.revScore())==2) {
                score2 += 1L;
            } else if ((int)Math.round(reviewDto.revScore())==1) {
                score1 += 1L;
            }
        }

        scoreSum = score1 + score2 + score3 + score4 + score5;
        map.addAttribute("scoreSum",scoreSum);
        map.addAttribute("score5",score5);
        map.addAttribute("score4",score4);
        map.addAttribute("score3",score3);
        map.addAttribute("score2",score2);
        map.addAttribute("score1",score1);




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
                       @RequestParam(value = "page",required = false) Integer page,
                       @RequestParam(value="size",required = false) Integer size,
                       @RequestParam(value="sort",required = false) String sort,
                       ModelMap map,String resaBisName) {
        PageRequest pageRequest=null;
        //System.out.println("sort 값은? : " + sort);
        String filtername=null;
        Page<BistroInfoDto> list=null;
        try {
            if (sort.equals("regDate")) {
                System.out.println("1");
                pageRequest = PageRequest.of(page, size, Sort.by(sort).descending());
                filtername = "최근 등록순";
            } else if (sort.equals("revScoreGood")) {
                System.out.println("2");
                pageRequest = PageRequest.of(page, size, Sort.by("revScore").descending());
                filtername = "별점 높은순";
            } else if (sort.equals("revScoreBad")) {
                System.out.println("3");
                pageRequest = PageRequest.of(page, size, Sort.by("revScore"));
                filtername = "별점 낮은순";
            }
            //System.out.println("pageable: "+ pageable);

            list = bistroInfoLogicService.shopList(pageRequest);
        }catch (Exception e){
            list = bistroInfoLogicService.shopList(pageable);
        }
//        Page<BistroInfoDto> fisrtList = bistroInfoLogicService.shopList(pageable);
        List<BistroInfo> cntlist = bistroInfoLogicService.shopList();
        //List<ReviewDto> reviewList = reviewLogicService.reviewList();
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), list.getTotalPages());

        System.out.println(list);
        map.addAttribute("shopList", list);
//        map.addAttribute("fisrtList", fisrtList);
        map.addAttribute("paginationBarNumbers",barNumbers);
        map.addAttribute("filtername",filtername);

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
//        map.addAttribute("reviews",rlists);
        map.addAttribute("avgpoint",average);
        map.addAttribute("revcnt",cnt);

        List<Photo> photos = photoLogicService.photoDto(resaBisName);
        map.addAttribute("shopPic",photos.get(0).getSavedPath());
        return "shop/shop";

    }




}
