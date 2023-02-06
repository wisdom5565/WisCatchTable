package com.catchmind.catchtable.controller;

import com.catchmind.catchtable.domain.Follow;
import com.catchmind.catchtable.dto.*;
import com.catchmind.catchtable.dto.network.request.CommentHeartRequest;
import com.catchmind.catchtable.dto.network.request.FollowRequest;
import com.catchmind.catchtable.dto.network.request.ReviewHeartRequest;
import com.catchmind.catchtable.dto.network.response.*;
import com.catchmind.catchtable.dto.security.CatchPrincipal;
import com.catchmind.catchtable.service.PaginationService;
import com.catchmind.catchtable.service.TimeLineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
@Slf4j
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
    public String timeline(ModelMap map, @AuthenticationPrincipal CatchPrincipal catchPrincipal,
                           @PageableDefault(size = 10, sort = "revIdx", direction = Sort.Direction.DESC) Pageable pageable) {
        Long prIdx = catchPrincipal.prIdx();
        Page<ReviewResponse> reviews = timeLineService.getReviews(pageable, prIdx);
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), reviews.getTotalPages());

//        map.addAttribute("header", header);
        map.addAttribute("reviews", reviews);
        map.addAttribute("prIdx", prIdx);
        map.addAttribute("paginationBarNumbers", barNumbers);
        return "timeline/timeline-main";
    }

    // 프로필페이지
    @GetMapping("/profile/{timeLineIdx}")
    public String profile(@PathVariable Long timeLineIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal, ModelMap map) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        TimeLineResponse response = timeLineService.getHeader(timeLineIdx);
        boolean isFollow = false;
        List<FollowDto> loginFollowing = timeLineService.getFollowingList(prIdx);
        for (FollowDto follow : loginFollowing) {
            System.out.println("following" + follow.following());
            System.out.println("follower" + follow.follower());
            if (timeLineIdx == follow.follower().prIdx()) {
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

    // 공개된 컬렉션 페이지
    @GetMapping("/collection/{timeLineIdx}")
    public String collection(@PathVariable Long timeLineIdx,
                             @PageableDefault(size = 10, sort = "colIdx", direction = Sort.Direction.DESC) Pageable pageable, ModelMap map) {
        Page<MyCollectionDto> collectionDtos = timeLineService.getCollection(timeLineIdx, pageable);
//        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), collectionDtos.getTotalPages());
        String prNick = timeLineService.getHeader(timeLineIdx).prNick();
        map.addAttribute("collections", collectionDtos);
        map.addAttribute("prNick", prNick);
//        map.addAttribute("paginationBarNumbers",barNumbers);
        System.out.println(collectionDtos);
        return "timeline/profile-collection";
    }

    // 프로필 리뷰 페이지
    @GetMapping("/review/{timeLineIdx}")
    public String review(@PathVariable Long timeLineIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal, ModelMap map,
                         @PageableDefault(size = 10, sort = "colIdx", direction = Sort.Direction.DESC) Pageable pageable) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        Page<ReviewResponse> response = timeLineService.getReview(timeLineIdx, pageable);
        List<Integer> barNumbers = paginationService.getPaginationBarNumber(pageable.getPageNumber(), response.getTotalPages());
        TimeLineResponse profile = timeLineService.getHeader(timeLineIdx);

        map.addAttribute("header", header);
        map.addAttribute("profile", profile);
        map.addAttribute("reviews", response);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("prIdx", prIdx);
        map.addAttribute("timeLineIdx", timeLineIdx);
        return "timeline/profile-review";
    }

    // 팔로잉 리스트
    @GetMapping("/following/{timeLineIdx}")
    public String following(@PathVariable Long timeLineIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal, ModelMap map) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        TimeLineResponse profile = timeLineService.getHeader(timeLineIdx);

        List<FollowDto> followingDtos = timeLineService.getFollowerList(timeLineIdx);
        List<FollowDto> loginDtos = timeLineService.getFollowerList(prIdx);
        List<FollowResponse> followingList = new ArrayList<>();
        boolean isFollow = false;
        for (FollowDto follow : followingDtos) {
            for (FollowDto login : loginDtos) {
                if (follow.following().prIdx() == login.following().prIdx()) {
                    isFollow = true;
                    break;
                }
            }
            FollowResponse response = new FollowResponse(follow.following().prIdx(), follow.following().prNick(), follow.following().prIntro(), isFollow);
            followingList.add(response);
        }

        System.out.println("비교 후 리스트 : " + followingList);

        map.addAttribute("header", header);
        map.addAttribute("prIdx", prIdx);
        map.addAttribute("profile", profile);
        map.addAttribute("timeLineIdx", timeLineIdx);
        map.addAttribute("followings", followingList);
        return "timeline/followingList";
    }

    // 팔로워 리스트
    @GetMapping("/follower/{timeLineIdx}")
    public String follower(@PathVariable Long timeLineIdx, @AuthenticationPrincipal CatchPrincipal catchPrincipal, ModelMap map) {
        Long prIdx = catchPrincipal.prIdx();
        TimeLineResponse header = header(prIdx);
        TimeLineResponse profile = timeLineService.getHeader(timeLineIdx);

        List<FollowDto> followerDtos = timeLineService.getFollowingList(timeLineIdx);
        List<FollowDto> loginDtos = timeLineService.getFollowerList(prIdx);
        List<FollowResponse> followerList = new ArrayList<>();
        boolean isFollow = false;
        for (FollowDto follow : followerDtos) {
            for (FollowDto login : loginDtos) {
                if (follow.follower().prIdx() == login.following().prIdx()) {
                    isFollow = true;
                    break;
                }
            }
            FollowResponse response = new FollowResponse(follow.follower().prIdx(), follow.follower().prNick(), follow.follower().prIntro(), isFollow);
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

    // 팔로우 기능
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

    // 언팔로우 기능
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

    // 좋아요 및 댓글 갯수 리스트
    @GetMapping("/review/total")
    @ResponseBody
    public List<ReviewHeartWithCommResponse> heartWithComm(@AuthenticationPrincipal CatchPrincipal principal) {
        Long prIdx = principal.prIdx();
        List<ReviewDto> reviews = timeLineService.reviews();
        List<ReviewHeartDto> hearts = timeLineService.getReviewHeart(prIdx);        // 좋아요 여부 판단을 위한 코드
        List<ReviewHeartWithCommResponse> totalList = new ArrayList<>();

        for (ReviewDto review : reviews) {
            boolean isLike = false;
            for (ReviewHeartDto heart : hearts) {
                if (review.revIdx().equals(heart.reviewDto().revIdx())) {
                    isLike = true;
                }
            }
            ReviewHeartWithCommResponse response = new ReviewHeartWithCommResponse(prIdx, review.revIdx(), isLike, review.revLike(), review.revComm());
            totalList.add(response);
        }
        log.warn("좋아요 여부 검사 후 전체 리뷰 리스트 : " + totalList);
        return totalList;
    }


    // 전체 댓글 리스트, 좋아요 여부 판단
    @GetMapping("/review/comment")
    @ResponseBody
    public List<CommentResponse> commentList(@AuthenticationPrincipal CatchPrincipal catchPrincipal) {
        Long prIdx = catchPrincipal.prIdx();
        List<CommentDto> comments = timeLineService.getComments();
        List<CommentHeartDto> comHeart = timeLineService.getComHeart(prIdx);        // 로그인한 유저의 좋아요 판별
        List<CommentResponse> comList = new ArrayList<>();

        for (CommentDto com : comments) {
            boolean isComm = false;
            if (com.profileDto().prIdx().equals(prIdx)) {
                isComm = true;
            }
            boolean isComLike = false;
            for (CommentHeartDto heart : comHeart) {
                if (com.comIdx().equals(heart.commentDto().comIdx())) {
                    isComLike = true;
                }
            }
            CommentResponse response = new CommentResponse(com.comIdx(),
                    com.profileDto().prNick(), com.profileDto().prIdx(), com.comContent(), com.reviewDto().revIdx(), com.regDate(),
                    com.comLike(), isComm, isComLike);
            comList.add(response);
        }
        log.warn("댓글 작성 유무 및 좋아요 판별 댓글 리스트 " + comList);
        return comList;
    }


    // 좋아요 기능
    @PostMapping(path = "/new/heart")
    @ResponseBody
    public Long newHeart(@RequestBody ReviewHeartRequest request) {
        System.out.println("❤️" + request.revLike());
        Long response = timeLineService.newHeart(request);
        System.out.println(response);
        return response;
    }

    //좋아요 삭제
    @PostMapping(path = "/del/heart")
    @ResponseBody
    public Long deleteHeart(@RequestBody ReviewHeartRequest request) {
        System.out.println("💙" + request.revLike());
        System.out.println(request);
        Long response = timeLineService.delHeart(request);
        return response;
    }

    // 댓글 등록
    @PostMapping(path = "/new/comment")
    @ResponseBody
    public Long newComment(@RequestBody CommentHeartRequest request) {
        System.out.println(request);
        Long response = timeLineService.newComment(request);
        return response;
    }

    // 댓글 삭제
    @GetMapping("/del/comment/{comIdx}/{revIdx}")
    @ResponseBody
    public Long delComm(@PathVariable Long comIdx, @PathVariable Long revIdx) {
        System.out.println("삭제" + comIdx);
        System.out.println("삭제" + revIdx);
        Long com = timeLineService.delComment(comIdx, revIdx);
        return com;
    }

    // 댓글 좋아요
    @PostMapping("/new/comment/heart")
    @ResponseBody
    public Long newComHeart(@RequestBody CommentHeartRequest request) {
        System.out.println("❤️" + request.comLike());
        Long response = timeLineService.newComHeart(request);
        System.out.println(response);
        return response;
    }

    // 댓글 좋아요삭제
    @PostMapping("/del/comment/heart")
    @ResponseBody
    public Long delComHeart(@RequestBody CommentHeartRequest request) {
        System.out.println("💙" + request.comLike());
        Long response = timeLineService.delComHeart(request);
        System.out.println(response);
        return response;
    }

    // 리뷰삭제
    @GetMapping("/del/review/{revIdx}/{resIdx}")
    @ResponseBody
    public String delReview(@PathVariable Long revIdx, @PathVariable Long resIdx) {
        System.out.println("삭제" + revIdx);
        System.out.println("삭제" + resIdx);
        timeLineService.delReview(revIdx, resIdx);
        return "OK";
    }

    // 새로운 댓글
//    @GetMapping(path = "/review/get/comment/{comIdx}")
//    @ResponseBody
//    public CommentResponse getComment(@PathVariable Long comIdx) {
//        System.out.println(comIdx);
//        CommentDto response = timeLineService.getComment(comIdx);
//        CommentResponse newCom = new CommentResponse(response.comIdx(), response.profileDto().prNick(),
//                response.comContent(), response.reviewDto().revIdx(), response.regDate(), response.comLike(), true, false);
//        return newCom;
//    }

}
