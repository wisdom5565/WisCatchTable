package com.catchmind.catchtable.controller;

import com.catchmind.catchtable.domain.Follow;
import com.catchmind.catchtable.dto.*;
import com.catchmind.catchtable.dto.network.request.FollowRequest;
import com.catchmind.catchtable.dto.network.response.FollowResponse;
import com.catchmind.catchtable.dto.network.response.ProfileTimeLineResponse;
import com.catchmind.catchtable.dto.network.response.ReviewResponse;
import com.catchmind.catchtable.dto.network.response.TimeLineResponse;
import com.catchmind.catchtable.dto.security.CatchPrincipal;
import com.catchmind.catchtable.service.PaginationService;
import com.catchmind.catchtable.service.TimeLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("timeline")
@RequiredArgsConstructor
public class TimelineController {
    private final TimeLineService timeLineService;
    private final PaginationService paginationService;

    // 타임라인 헤더
    public TimeLineResponse header(Long prIdx) {
        TimeLineResponse response = timeLineService.getHeader(prIdx);
        return response;
    }

    // 전체 리뷰 페이지
    @GetMapping("")
    public String timeline(ModelMap map,
                           @AuthenticationPrincipal CatchPrincipal catchPrincipal,
                           @PageableDefault(size = 10, sort = "revIdx", direction = Sort.Direction.DESC) Pageable pageable) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        Page<ReviewResponse> reviews = timeLineService.getReviews(pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());
        System.out.println(reviews);
        map.addAttribute("header", header);
        map.addAttribute("reviews", reviews);
        map.addAttribute("prIdx", prIdx);
        map.addAttribute("paginationBarNumbers",barNumbers);
        return "timeline/timeline-main";
    }

    // 전체에서 다른사람 프로필 선택시 페이지
    @GetMapping("/profile/{timeLineIdx}")
    public String profile(@PathVariable Long timeLineIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal, ModelMap map) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        TimeLineResponse response = timeLineService.getHeader(timeLineIdx);
        boolean isFollow = false;
        List<FollowDto> loginFollowing = timeLineService.getFollowingList(prIdx);
        for(FollowDto follow :  loginFollowing) {
            if(timeLineIdx == follow.following().prIdx()) {
                isFollow = true;
            }
        }
        System.out.println(isFollow);

        map.addAttribute("header", header);
        map.addAttribute("profile", response);
        map.addAttribute("timeLineIdx", timeLineIdx);
        map.addAttribute("prIdx", prIdx);
        map.addAttribute("isFollow", isFollow);
        return "timeline/profile";
    }

    // 다른 사람 컬렉션
    @GetMapping("/collection/{timeLineIdx}")
    public String collection(@PathVariable Long timeLineIdx,
                             @PageableDefault(size = 10, sort = "colIdx", direction = Sort.Direction.DESC) Pageable pageable
            , @AuthenticationPrincipal CatchPrincipal catchPrincipal, ModelMap map) {
        Long prIdx = catchPrincipal.prIdx();
        List<CollectionDto> collectionDtos = timeLineService.getCollection(timeLineIdx, pageable);
//        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), collectionDtos.getTotalPages());
        TimeLineResponse profile = timeLineService.getHeader(timeLineIdx);
        TimeLineResponse header = header(prIdx);

        map.addAttribute("collections", collectionDtos);
        map.addAttribute("header", header);
        map.addAttribute("profile", profile);
        map.addAttribute("prIdx", prIdx);
//        map.addAttribute("paginationBarNumbers",barNumbers);
        System.out.println(collectionDtos);
        return "timeline/profile-collection";
    }

    // 다른사람 리뷰
    @GetMapping("/review/{timeLineIdx}")
    public String review(@PathVariable Long timeLineIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal, ModelMap map,
                         @PageableDefault(size = 10, sort = "colIdx", direction = Sort.Direction.DESC) Pageable pageable) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        Page<ReviewResponse> response = timeLineService.getReview(timeLineIdx,pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), response.getTotalPages());
        TimeLineResponse profile = timeLineService.getHeader(timeLineIdx);

        map.addAttribute("header", header);
        map.addAttribute("profile", profile);
        map.addAttribute("reviews", response);
        map.addAttribute("paginationBarNumbers",barNumbers);
        map.addAttribute("prIdx", prIdx);
        map.addAttribute("timeLineIdx", timeLineIdx);
        return "timeline/profile-review";
    }

    @GetMapping("/following/{timeLineIdx}")
    public String following(@PathVariable Long timeLineIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal, ModelMap map) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        TimeLineResponse profile = timeLineService.getHeader(timeLineIdx);

        List<FollowDto> followingDtos = timeLineService.getFollowerList(timeLineIdx);
        List<FollowDto> loginDtos =  timeLineService.getFollowingList(prIdx);
        List<FollowResponse> followingList = new ArrayList<>();
        boolean isFollow = false;
            for (FollowDto follow : followingDtos) {
                for (FollowDto login : loginDtos) {
                    if (follow.following().prIdx() == login.following().prIdx()) {
                        isFollow = true;
                    }
                }
                FollowResponse response = new FollowResponse(follow.following().prIdx(), follow.following().prNick(), follow.following().prNick(), isFollow);
                followingList.add(response);
            }

        System.out.println("비교 후 리스트 : " +followingList);

        map.addAttribute("header", header);
        map.addAttribute("prIdx", prIdx);
        map.addAttribute("profile", profile);
        map.addAttribute("timeLineIdx", timeLineIdx);
        map.addAttribute("followings", followingList);
        return "timeline/followingList";
    }

    @GetMapping("/follower/{timeLineIdx}")
    public String follower(@PathVariable Long timeLineIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal, ModelMap map) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        TimeLineResponse profile = timeLineService.getHeader(timeLineIdx);

        List<FollowDto> followerDtos = timeLineService.getFollowingList(timeLineIdx);
        List<FollowDto> loginDtos = timeLineService.getFollowingList(prIdx);
        List<FollowResponse> followerList = new ArrayList<>();
        boolean isFollow = false;
        for (FollowDto follow : followerDtos) {
            for (FollowDto login : loginDtos) {
                if (follow.follower().prIdx() == login.following().prIdx()) {
                    isFollow = true;
                }
            }
            FollowResponse response = new FollowResponse(follow.follower().prIdx(), follow.follower().prNick(), follow.follower().prNick(), isFollow);
            followerList.add(response);
        }
        System.out.println("검사후 팔로워 리스트 : " + followerList);


        map.addAttribute("header", header);
        map.addAttribute("prIdx", prIdx);
        map.addAttribute("timeLineIdx", timeLineIdx);
        map.addAttribute("followers", followerList);
        map.addAttribute("profile", profile);
        return "timeline/followerList";
    }

    @PostMapping(path = "/following")
    @ResponseBody
    public String follow(@RequestBody FollowRequest request) {
        System.out.println(request);
        Follow response = timeLineService.follow(request);
        if (response != null) {
            return "OK";
        }
        return "NO";
    }

    @PostMapping(path = "/unfollowing")
    @ResponseBody
    public String unfollow(@RequestBody FollowRequest request) {
        System.out.println(request);
        Optional<Follow> response = timeLineService.unfollow(request);
        if (response != null) {
            return "OK";
        }
        return "NO";
    }

}
