package com.catchmind.catchtable.controller;

import com.catchmind.catchtable.domain.BistroInfo;
import com.catchmind.catchtable.domain.BistroSave;
import com.catchmind.catchtable.dto.*;
import com.catchmind.catchtable.dto.network.request.BistroSaveRequest;
import com.catchmind.catchtable.dto.network.response.ReviewResponse;
import com.catchmind.catchtable.dto.network.response.ShopListResponse;
import com.catchmind.catchtable.dto.network.response.ShopResponse;
import com.catchmind.catchtable.dto.network.response.ShopReviewResponse;
import com.catchmind.catchtable.dto.security.CatchPrincipal;
import com.catchmind.catchtable.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    private final ShopService shopService;
    private final BistroSaveService bistroSaveService;

    // 식당별 리뷰 프로그레스만 뺀 메소드
    public ShopReviewResponse reviewProgress(String resaBisName) {
        List<ReviewDto> lists = reviewLogicService.reviewList(resaBisName);
        Double sum = 0.0;
        Double avg = 0.0;
        int cnt = 0;
        for (ReviewDto review : lists) {
            sum += review.revScore();
            cnt += 1;
        }
        avg = sum / cnt;
        String average = String.format("%.1f", avg);
        //그래프
        Long score5 = 0L;
        Long score4 = 0L;
        Long score3 = 0L;
        Long score2 = 0L;
        Long score1 = 0L;
        Long scoreSum = 0L;

        for (ReviewDto reviewDto : lists) {
            if ((int) Math.round(reviewDto.revScore()) == 5) {
                score5 += 1L;
            } else if ((int) Math.round(reviewDto.revScore()) == 4) {
                score4 += 1L;
            } else if ((int) Math.round(reviewDto.revScore()) == 3) {
                score3 += 1L;
            } else if ((int) Math.round(reviewDto.revScore()) == 2) {
                score2 += 1L;
            } else if ((int) Math.round(reviewDto.revScore()) == 1) {
                score1 += 1L;
            }
        }
        scoreSum = score1 + score2 + score3 + score4 + score5;
        ShopReviewResponse response = new ShopReviewResponse(average, cnt, score1, score2, score3, score4, score5, scoreSum);
        return response;
    }

    // 식당별 리뷰
    @GetMapping("/review/{resaBisName}")
    public String shopReview(@PageableDefault(size = 10) Pageable pageable,
                             @RequestParam(value = "sort", required = false) String sort,
                             ModelMap map,
                             @PathVariable String resaBisName,
                             @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Page<ReviewResponse> reviews =  null;
        ShopReviewResponse response = reviewProgress(resaBisName);
        String filtername = "최근 등록순";
        if (sort == null) {
            if (catchPrincipal == null) {
                reviews = shopService.getBisNameReviews(pageable, resaBisName, null, sort);
                List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());
                map.addAttribute("reviews",reviews);
                map.addAttribute("paginationBarNumbers",barNumbers);
                map.addAttribute("sort", sort);
                map.addAttribute("filtername", filtername);
                map.addAttribute("prIdx", 0);
            } else {
                Long prIdx = catchPrincipal.prIdx();
                reviews = shopService.getBisNameReviews(pageable, resaBisName, prIdx, sort);
                List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());
                map.addAttribute("reviews",reviews);
                map.addAttribute("paginationBarNumbers",barNumbers);
                map.addAttribute("sort", sort);
                map.addAttribute("filtername", filtername);
                map.addAttribute("prIdx", prIdx);
            }
        } else {
            switch(sort) {
                case "regDate" :
                    if (catchPrincipal == null) {
                        reviews = shopService.getBisNameReviews(pageable, resaBisName, null, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());
                        map.addAttribute("reviews",reviews);
                        map.addAttribute("paginationBarNumbers",barNumbers);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                    } else {
                        Long prIdx = catchPrincipal.prIdx();
                        reviews = shopService.getBisNameReviews(pageable, resaBisName, prIdx, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());
                        map.addAttribute("reviews",reviews);
                        map.addAttribute("paginationBarNumbers",barNumbers);
                        map.addAttribute("sort", sort);
                        map.addAttribute("prIdx", prIdx);
                        map.addAttribute("filtername", filtername);
                    }
                    break;
                case "highScore":
                    filtername = "별점 높은 순";
                    if (catchPrincipal == null) {
                        reviews = shopService.getBisNameReviews(pageable, resaBisName, null, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());
                        map.addAttribute("reviews",reviews);
                        map.addAttribute("paginationBarNumbers",barNumbers);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                    } else {
                        Long prIdx = catchPrincipal.prIdx();
                        reviews = shopService.getBisNameReviews(pageable, resaBisName, prIdx, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());
                        map.addAttribute("reviews",reviews);
                        map.addAttribute("paginationBarNumbers",barNumbers);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                        map.addAttribute("prIdx", prIdx);
                    }
                    break;
                case "lowScore":
                    filtername = "별점 낮은 순";
                    if (catchPrincipal == null) {
                        reviews = shopService.getBisNameReviews(pageable, resaBisName, null, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());
                        map.addAttribute("reviews",reviews);
                        map.addAttribute("paginationBarNumbers",barNumbers);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                    } else {
                        Long prIdx = catchPrincipal.prIdx();
                        reviews = shopService.getBisNameReviews(pageable, resaBisName, prIdx, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());
                        map.addAttribute("reviews",reviews);
                        map.addAttribute("paginationBarNumbers",barNumbers);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                        map.addAttribute("prIdx", prIdx);
                    }
                    break;
            }
        }
        map.addAttribute("resaBisName", resaBisName);
        map.addAttribute("progress", response);

        return "shop/shopReview";
    }

    // 식당별 메뉴리스트
    @GetMapping("/menulist/{resaBisName}")
    public String menu(@PathVariable String resaBisName, ModelMap map) {
        System.out.println("inside");
        List<MenuDto> list = menuLogicService.menuList(resaBisName);
        map.addAttribute("menu", list);
        map.addAttribute("resaBisName", resaBisName);
        System.out.println(list);
        return "shop/menuAllList";

    }

    // 식당 리스트
    @GetMapping("/list")
    public String list(@PageableDefault(size = 10) Pageable pageable,
                       ModelMap map,
                       @AuthenticationPrincipal CatchPrincipal catchPrincipal,
                       @RequestParam(required = false) String sort,
                       @RequestParam(required = false) String bisCategory,
                       @RequestParam(required = false) String bisRegion) {
        String filtername = "최신등록순";
        if (sort == "" || sort == null) {
            if (catchPrincipal == null) {
                Page<ShopListResponse> shopList = shopService.shopList(pageable, null, null);
                List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), shopList.getTotalPages());
                map.addAttribute("shopList", shopList);
                map.addAttribute("paginationBarNumbers", barNumbers);
                map.addAttribute("prIdx", 0);
                map.addAttribute("sort", sort);
                map.addAttribute("filtername", filtername);
            } else {
                Long prIdx = catchPrincipal.prIdx();
                Page<ShopListResponse> shopList = shopService.shopList(pageable, prIdx, null);
                List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), shopList.getTotalPages());
                map.addAttribute("shopList", shopList);
                map.addAttribute("paginationBarNumbers", barNumbers);
                map.addAttribute("prIdx", prIdx);
                map.addAttribute("sort", sort);
                map.addAttribute("filtername", filtername);
            }
        } else {
            switch (sort) {
                case "regDate":
                    if (catchPrincipal == null) {
                        Page<ShopListResponse> shopList = shopService.shopList(pageable, null, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), shopList.getTotalPages());
                        map.addAttribute("shopList", shopList);
                        map.addAttribute("paginationBarNumbers", barNumbers);
                        map.addAttribute("prIdx", 0);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                    } else {
                        Long prIdx = catchPrincipal.prIdx();
                        Page<ShopListResponse> shopList = shopService.shopList(pageable, prIdx, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), shopList.getTotalPages());
                        map.addAttribute("shopList", shopList);
                        map.addAttribute("paginationBarNumbers", barNumbers);
                        map.addAttribute("prIdx", prIdx);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                    }
                    break;
                case "revCnt":
                    if (catchPrincipal == null) {
                        Page<ShopListResponse> shopList = shopService.shopList(pageable, null, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), shopList.getTotalPages());
                        filtername = "리뷰 많은순";
                        map.addAttribute("shopList", shopList);
                        map.addAttribute("paginationBarNumbers", barNumbers);
                        map.addAttribute("prIdx", 0);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                    } else {
                        Long prIdx = catchPrincipal.prIdx();
                        Page<ShopListResponse> shopList = shopService.shopList(pageable, prIdx, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), shopList.getTotalPages());
                        filtername = "리뷰 많은순";
                        map.addAttribute("shopList", shopList);
                        map.addAttribute("paginationBarNumbers", barNumbers);
                        map.addAttribute("prIdx", prIdx);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                    }
                    break;
                case "revScore":
                    if (catchPrincipal == null) {
                        Page<ShopListResponse> shopList = shopService.shopList(pageable, null, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), shopList.getTotalPages());
                        filtername = "별점 높은순";
                        map.addAttribute("shopList", shopList);
                        map.addAttribute("paginationBarNumbers", barNumbers);
                        map.addAttribute("prIdx", 0);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                    } else {
                        Long prIdx = catchPrincipal.prIdx();
                        Page<ShopListResponse> shopList = shopService.shopList(pageable, prIdx, sort);
                        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), shopList.getTotalPages());
                        filtername = "별점 높은순";
                        map.addAttribute("shopList", shopList);
                        map.addAttribute("paginationBarNumbers", barNumbers);
                        map.addAttribute("prIdx", prIdx);
                        map.addAttribute("sort", sort);
                        map.addAttribute("filtername", filtername);
                    }
            }

        }
        return "shop/list";
    }

//    @GetMapping("/list/{bisCategory}")
//    public String list(@PageableDefault(size = 10, sort = "bisIdx", direction = Sort.Direction.DESC) Pageable pageable,
//                       @RequestParam(value = "page", required = false) Integer page,
//                       @RequestParam(value = "size", required = false) Integer size,
//                       @RequestParam(value = "sort", required = false) String sort,
//                       ModelMap map, @PathVariable String bisCategory,
//                       @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
//        PageRequest pageRequest = null;
//        String filtername = null;
//        Page<BistroInfoDto> list = null;
//        bistroInfoLogicService.getReview();
//
//        //세션으로 prIdx 불러오기
//        Long prIdx = catchPrincipal.prIdx();
//        map.addAttribute("prIdx", prIdx);
//
//        try {
//            if (sort != null) {
//                System.out.println("sort가 널이 아님");
//                map.addAttribute("sort", sort);
//
//            }
//        } catch (Exception e) {
//            System.out.println("sort is null!");
//            map.addAttribute("sort", "regDate");
//        }
//        try {
//            if (sort.equals("regDate")) {
//                System.out.println("1");
//                pageRequest = PageRequest.of(page, size, Sort.by("regDate").descending());
//                filtername = "최근 등록순";
//            } else if (sort.equals("revScoreGood")) {
//                System.out.println("2");
//                pageRequest = PageRequest.of(page, size, Sort.by("regDate").descending());
//                filtername = "별점 높은순";
//            }
//            list = bistroInfoLogicService.shopCategoryList(bisCategory, pageRequest);
//        } catch (Exception e) {
//            System.out.println("페이저블 겟쏘트" + pageable.getSort());
//            pageRequest = PageRequest.of(0, 10, Sort.by("regDate").descending());
//            list = bistroInfoLogicService.shopCategoryList(bisCategory, pageRequest);
//            map.addAttribute("sort", "regDate");
//        }
//        System.out.println("listgetnumber = " + list.getNumber());
//        Page<BistroInfoDto> bistroInfos = bistroInfoRepository.findAllByBisCategoryContaining(bisCategory, pageable).map(BistroInfoDto::from);
//        System.out.println("llist.getTotalPages() : " + list.getTotalPages());
//        List<BistroInfo> cntlist = bistroInfoLogicService.shopCountList(bisCategory);
//        //List<ReviewDto> reviewList = reviewLogicService.reviewList();
//        List<Integer> barNumbers;
//        try {
//            barNumbers = paginationService.getPaginationBarNumber(page, bistroInfos.getTotalPages());
//        } catch (Exception e) {
//            barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), bistroInfos.getTotalPages());
//            map.addAttribute("sort", "regDate");
//        }
//
//        //
//        List<BistroSave> bistroSavesList = bistroSaveRepository.findAllByProfile_PrIdx(prIdx);
//
//        List<BookmarkDto> booklist = new ArrayList<BookmarkDto>();
//        for (BistroInfoDto info : list) {
//            boolean temp = false;
//            for (BistroSave save : bistroSavesList) {
//                if (info.resAdminDto().resaBisName().equals(save.getResAdmin().getResaBisName())) {
//                    temp = true;
//                    break;
//                }
//            }
//            if (temp) {
//                booklist.add(new BookmarkDto(info, true));
//            } else {
//                booklist.add(new BookmarkDto(info, false));
//            }
//        }
//
//        map.addAttribute("bisCategory", bisCategory);
//        map.addAttribute("bistroSavesList", bistroSavesList);
//        map.addAttribute("booklist", booklist);
//        map.addAttribute("shopList", list);
//        map.addAttribute("paginationBarNumbers", barNumbers);
//        map.addAttribute("filtername", filtername);
//        map.addAttribute("totalpage", bistroInfos.getTotalPages());
//        map.addAttribute("pageable", pageable);
//        // 총 레스토랑 수
//        int cnt = cntlist.size();
//        map.addAttribute("cnt", cnt);
//
//        return "shop/list";
//    }
//
//    @GetMapping("/list/region/{bisRegion}")
//    public String regionList(@PageableDefault(size = 10, sort = "bisIdx", direction = Sort.Direction.DESC) Pageable pageable,
//                             @RequestParam(value = "page", required = false) Integer page,
//                             @RequestParam(value = "size", required = false) Integer size,
//                             @RequestParam(value = "sort", required = false) String sort,
//                             ModelMap map, @PathVariable String bisRegion,
//                             @AuthenticationPrincipal CatchPrincipal catchPrincipal) {
//        PageRequest pageRequest = null;
//        String filtername = null;
//        Page<BistroInfoDto> list = null;
//        bistroInfoLogicService.getReview();
//        //세션으로 prIdx 불러오기
//        Long prIdx = catchPrincipal.prIdx();
//        map.addAttribute("prIdx", prIdx);
//
//        try {
//            if (sort != null) {
//                System.out.println("sort가 널이 아님");
//                map.addAttribute("sort", sort);
//
//            }
//        } catch (Exception e) {
//            System.out.println("sort is null!");
//            map.addAttribute("sort", "regDate");
//        }
//        try {
//            if (sort.equals("regDate")) {
//                System.out.println("1");
//                pageRequest = PageRequest.of(page, size, Sort.by("regDate").descending());
//                filtername = "최근 등록순";
//            } else if (sort.equals("revScoreGood")) {
//                System.out.println("2");
//                pageRequest = PageRequest.of(page, size, Sort.by("regDate").descending());
//                filtername = "별점 높은순";
//            }
//            list = bistroInfoLogicService.shopRegionList(bisRegion, pageRequest);
//        } catch (Exception e) {
//            System.out.println("페이저블 겟쏘트" + pageable.getSort());
//            pageRequest = PageRequest.of(0, 10, Sort.by("regDate").descending());
//            list = bistroInfoLogicService.shopRegionList(bisRegion, pageRequest);
//            map.addAttribute("sort", "regDate");
//        }
//        System.out.println("listgetnumber = " + list.getNumber());
//        Page<BistroInfoDto> bistroInfos = bistroInfoRepository.findAllByBisRegionContaining(bisRegion, pageable).map(BistroInfoDto::from);
//        System.out.println("llist.getTotalPages() : " + list.getTotalPages());
//        List<BistroInfo> cntlist = bistroInfoLogicService.shopCountList(bisRegion);
//        //List<ReviewDto> reviewList = reviewLogicService.reviewList();
//        List<Integer> barNumbers;
//        try {
//            barNumbers = paginationService.getPaginationBarNumber(page, bistroInfos.getTotalPages());
//        } catch (Exception e) {
//            barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), bistroInfos.getTotalPages());
//            map.addAttribute("sort", "regDate");
//        }
//
//        //
//        List<BistroSave> bistroSavesList = bistroSaveRepository.findAllByProfile_PrIdx(prIdx);
//
//        List<BookmarkDto> booklist = new ArrayList<BookmarkDto>();
//        for (BistroInfoDto info : list) {
//            boolean temp = false;
//            for (BistroSave save : bistroSavesList) {
//                if (info.resAdminDto().resaBisName().equals(save.getResAdmin().getResaBisName())) {
//                    temp = true;
//                    break;
//                }
//            }
//            if (temp) {
//                booklist.add(new BookmarkDto(info, true));
//            } else {
//                booklist.add(new BookmarkDto(info, false));
//            }
//        }
//
//        map.addAttribute("bisCategory", bisRegion);
//        map.addAttribute("bistroSavesList", bistroSavesList);
//        map.addAttribute("booklist", booklist);
//        map.addAttribute("shopList", list);
//        map.addAttribute("paginationBarNumbers", barNumbers);
//        map.addAttribute("filtername", filtername);
//        map.addAttribute("totalpage", bistroInfos.getTotalPages());
//        map.addAttribute("pageable", pageable);
//        // 총 레스토랑 수
//        int cnt = cntlist.size();
//        map.addAttribute("cnt", cnt);
//
//        return "shop/list";
//    }

    // 식당 상세
    @GetMapping("/{resaBisName}")
    public String service(@PathVariable String resaBisName, ModelMap map) {

        BistroDetailDto lists = bistroDetailLogicService.detailList(resaBisName);
        map.addAttribute("bisDetail", lists);

        List<BisNoticeDto> nlists = bisNoticeLogicService.noticeList(resaBisName);
        map.addAttribute("bisNotice", nlists);

        List<MenuDto> listss = menuLogicService.menuList(resaBisName);
        map.addAttribute("menu", listss);

        List<FacilityDto> flist = facilityLogicService.facilityList(resaBisName);
        map.addAttribute("facility", flist);
        System.out.println("flist" + flist);

        //식당 평균 별점
        List<ReviewDto> rlists = reviewLogicService.reviewList(resaBisName);
        List<ShopResponse> reviewAndPhoto = reviewLogicService.reviewPhotoList(resaBisName);
        Double sum = 0.0;
        Double avg = 0.0;
        int cnt = 0;
        for (ReviewDto review : rlists) {
            sum += review.revScore();
            cnt += 1;
        }
        avg = sum / cnt;
        String average = String.format("%.1f", avg);
        map.addAttribute("reviews", rlists);
        map.addAttribute("avgpoint", average);
        map.addAttribute("revcnt", cnt);
        map.addAttribute("reviewAndPhoto", reviewAndPhoto);
        PhotoDto photos = photoLogicService.photoDto(resaBisName);
        map.addAttribute("shopPic", photos);
        System.out.println(photos);
        return "shop/shop";

    }

    // 식당 검색
    @GetMapping("/search/list")
    public String searchList(@RequestParam(value = "resaBisName", required = false) String resaBisName, @PageableDefault(size = 10, sort = "bisIdx", direction = Sort.Direction.DESC) Pageable pageable, Model map) {
        Page<BistroInfo> list = bistroInfoLogicService.searchList(resaBisName, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), list.getTotalPages());

        System.out.println(list);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("list", list);
        map.addAttribute("totalpage", list.getTotalPages());
        map.addAttribute("resaBisName", resaBisName);
        return "shop/search_list";
    }

    // 북마크 저장
    @PostMapping(path = "/new/bookmark")
    @ResponseBody
    public String newBookmark(@RequestBody BistroSaveRequest request) {
        System.out.println(request);
        BistroSave bistroSave = bistroSaveService.newBookmark(request);
        if (bistroSave != null) {
            return "OK";
        } else {
            return "NO";
        }
    }

    // 북마크 삭제
    @PostMapping(path = "/del/bookmark")
    @ResponseBody
    public String delBookmark(@RequestBody BistroSaveRequest request) {
        System.out.println("컨트롤러 진입" + request);
        Optional<BistroSave> bistroSave = bistroSaveService.delBookmark(request);
        if (bistroSave != null) {
            return "OK";
        } else {
            return "NO";
        }
    }

}
