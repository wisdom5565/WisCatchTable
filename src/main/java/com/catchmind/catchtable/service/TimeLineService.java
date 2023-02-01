package com.catchmind.catchtable.service;

import com.catchmind.catchtable.domain.Follow;
import com.catchmind.catchtable.domain.Review;
import com.catchmind.catchtable.dto.*;
import com.catchmind.catchtable.dto.network.request.FollowRequest;
import com.catchmind.catchtable.dto.network.response.ProfileTimeLineResponse;
import com.catchmind.catchtable.dto.network.response.ReviewResponse;
import com.catchmind.catchtable.dto.network.response.TimeLineResponse;
import com.catchmind.catchtable.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeLineService {
    private final ProfileRepository profileRepository;
    private final SnsRepository snsRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewHeartRepository reviewHeartRepository;
    private final CommentHeartRepository commentHeartRepository;
    private final FollowRepository followRepository;
    private final CommentRepository commentRepository;
    private final CollectionRepository collectionRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;

    @Transactional
    public TimeLineResponse getHeader(Long prIdx) {
        ProfileDto profileDto = profileRepository.findById(prIdx).map(ProfileDto::from).orElseThrow();
        List<SnsDto> snsList = snsRepository.findAllByProfile_PrIdx(prIdx).stream().map(SnsDto::from).toList();
        System.out.println("❌" + snsList);
        List<ReviewDto> reviewDtos = reviewRepository.findAllByProfile_PrIdx(prIdx).stream().map(ReviewDto::from).toList();
        Long followingNum = followRepository.countByFollower_PrIdx(prIdx);      // 로그인한 유저의 팔로잉 수
        Long followerNum = followRepository.countByFollowing_PrIdx(prIdx);      // 로그인한 유저의 팔로워 수

        double totalScore = 0;
        for (int i = 0; i < reviewDtos.size(); i++) {
            double score = reviewDtos.get(i).revScore();
            totalScore += score;
        }

        String prName = profileDto.prName();
        String prNick = profileDto.prNick();
        String prRegion = profileDto.prRegion();
        String prIntro = profileDto.prIntro();
        double avgScore = totalScore / reviewDtos.size();


        TimeLineResponse timeLineResponse = new TimeLineResponse(prName, prNick, prRegion, prIntro, followingNum, followerNum, snsList, avgScore);

        return timeLineResponse;
    }

//    public List<ReviewResponse> getReviews() {
//        List<ReviewResponse> reviewList = new ArrayList<>();
//        List<ReviewDto> reviewDtos = reviewRepository.findAll().stream().map(ReviewDto::from).toList();
//        List<ReviewPhotoDto> photoDtos = reviewPhotoRepository.findAll().stream().map(ReviewPhotoDto::from).toList();
//
//
//        for (int i = 0; i < reviewDtos.size(); i++) {
//            List<ReviewPhotoDto> photoList = new ArrayList<>();
//            for (int j = 0; j < photoDtos.size(); j++) {
//                if (reviewDtos.get(i).revIdx() == photoDtos.get(j).reviewDto().revIdx()) {
//                    String orgNm = photoDtos.get(j).orgNm();
//                    String savedNm = photoDtos.get(j).savedNm();
//                    String savedPath = photoDtos.get(j).savedPath();
//                    ReviewDto reviewDto = photoDtos.get(j).reviewDto();
//                    ReviewPhotoDto real = ReviewPhotoDto.of(orgNm, savedNm, savedPath, reviewDto);
//                    photoList.add(real);
//                }
//            }
//            if (photoList.isEmpty()) {
//                ReviewResponse response = new ReviewResponse(reviewDtos.get(i).revIdx(), reviewDtos.get(i).profileDto(), reviewDtos.get(i).revContent(), reviewDtos.get(i).revScore(),
//                        reviewDtos.get(i).resAdminDto(), null, reviewDtos.get(i).reserveDto().resIdx());
//                reviewList.add(response);
//            } else {
//                ReviewResponse response = new ReviewResponse(reviewDtos.get(i).revIdx(), reviewDtos.get(i).profileDto(), reviewDtos.get(i).revContent(), reviewDtos.get(i).revScore(),
//                        reviewDtos.get(i).resAdminDto(), photoList, reviewDtos.get(i).reserveDto().resIdx());
//                reviewList.add(response);
//            }
//        }
//
//        return reviewList;
//    }

    // 전체 리뷰 페이지 객체로 반환
    public Page<ReviewResponse> getReviews(Pageable pageable) {
        List<ReviewResponse> reviewList = new ArrayList<>();
        List<ReviewDto> reviewDtos = reviewRepository.findAll().stream().map(ReviewDto::from).toList();
        List<ReviewPhotoDto> photoDtos = reviewPhotoRepository.findAll().stream().map(ReviewPhotoDto::from).toList();

        for (int i = 0; i < reviewDtos.size(); i++) {
            List<ReviewPhotoDto> photoList = new ArrayList<>();
            for (int j = 0; j < photoDtos.size(); j++) {
                if (reviewDtos.get(i).revIdx() == photoDtos.get(j).reviewDto().revIdx()) {
                    String orgNm = photoDtos.get(j).orgNm();
                    String savedNm = photoDtos.get(j).savedNm();
                    String savedPath = photoDtos.get(j).savedPath();
                    ReviewDto reviewDto = photoDtos.get(j).reviewDto();
                    ReviewPhotoDto real = ReviewPhotoDto.of(orgNm, savedNm, savedPath, reviewDto);
                    photoList.add(real);
                }
            }
            if (photoList.isEmpty()) {
                ReviewResponse response = new ReviewResponse(reviewDtos.get(i).revIdx(), reviewDtos.get(i).profileDto(), reviewDtos.get(i).revContent(), reviewDtos.get(i).revScore(),
                        reviewDtos.get(i).resAdminDto(), null, reviewDtos.get(i).reserveDto().resIdx());
                reviewList.add(response);
            } else {
                ReviewResponse response = new ReviewResponse(reviewDtos.get(i).revIdx(), reviewDtos.get(i).profileDto(), reviewDtos.get(i).revContent(), reviewDtos.get(i).revScore(),
                        reviewDtos.get(i).resAdminDto(), photoList, reviewDtos.get(i).reserveDto().resIdx());
                reviewList.add(response);
            }
        }

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), reviewList.size());
        PageImpl<ReviewResponse> reviewResponsePage = new PageImpl<>(reviewList.subList(start, end),pageable,reviewList.size());
        return reviewResponsePage;
    }


    // 개인리뷰 조회
    public Page<ReviewResponse> getReview(Long prIdx,Pageable pageable) {
        List<ReviewResponse> reviewList = new ArrayList<>();
        List<ReviewDto> reviewDtos = reviewRepository.findAllByProfile_PrIdx(prIdx).stream().map(ReviewDto::from).toList();
        List<ReviewPhotoDto> photoDtos = reviewPhotoRepository.findAll().stream().map(ReviewPhotoDto::from).toList();


        for (int i = 0; i < reviewDtos.size(); i++) {
            List<ReviewPhotoDto> photoList = new ArrayList<>();
            for (int j = 0; j < photoDtos.size(); j++) {
                if (reviewDtos.get(i).revIdx() == photoDtos.get(j).reviewDto().revIdx()) {
                    String orgNm = photoDtos.get(j).orgNm();
                    String savedNm = photoDtos.get(j).savedNm();
                    String savedPath = photoDtos.get(j).savedPath();
                    ReviewDto reviewDto = photoDtos.get(j).reviewDto();
                    ReviewPhotoDto real = ReviewPhotoDto.of(orgNm, savedNm, savedPath, reviewDto);
                    photoList.add(real);
                }
            }
            if (photoList.isEmpty()) {
                ReviewResponse response = new ReviewResponse(reviewDtos.get(i).revIdx(), reviewDtos.get(i).profileDto(), reviewDtos.get(i).revContent(), reviewDtos.get(i).revScore(),
                        reviewDtos.get(i).resAdminDto(), null, reviewDtos.get(i).reserveDto().resIdx());
                reviewList.add(response);
            } else {
                ReviewResponse response = new ReviewResponse(reviewDtos.get(i).revIdx(), reviewDtos.get(i).profileDto(), reviewDtos.get(i).revContent(), reviewDtos.get(i).revScore(),
                        reviewDtos.get(i).resAdminDto(), photoList, reviewDtos.get(i).reserveDto().resIdx());
                reviewList.add(response);
            }
        System.out.println("i" + i + reviewList.get(i).photo());
        }

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), reviewList.size());
        PageImpl<ReviewResponse> reviewResponsePage = new PageImpl<>(reviewList.subList(start, end),pageable,reviewList.size());
        return reviewResponsePage;


//        System.out.println(reviewList);
//        List<Long> reviewIdx = new ArrayList<>();
//        List<CommentDto> commentDtos = new ArrayList<>();
//        List<ReviewHeartDto> heartDtos = new ArrayList<>();
//        for (int i = 0; i < reviewList.size(); i++) {
//            reviewIdx.add(reviewList.get(i).revIdx());
//        }
//        System.out.println("💢review 번호 : " + reviewIdx);
//
//        for (int i = 0; i < reviewIdx.size(); i++) {
//            commentDtos = commentRepository.findAllByReview_revIdx(reviewIdx.get(i)).stream().map(CommentDto::from).toList();
//            heartDtos = reviewHeartRepository.findAllByReview_revIdx(reviewIdx.get(i)).stream().map(ReviewHeartDto::from).toList();
//        }
//        int commNum = commentDtos.size();
//        int heartNum = heartDtos.size();
//
//        return new ProfileTimeLineResponse(reviewList, commNum, heartNum);
    }

    public List<CollectionDto> getCollection(Long prIdx, Pageable pageable) {
        List<CollectionDto> collectionDtos = collectionRepository.findAllByProfile_PrIdx(prIdx)
                .stream().map(CollectionDto::from).toList();
        return collectionDtos;
    }

    public List<FollowDto> getFollowingList(Long prIdx) {
        List<FollowDto> followingList = followRepository.findByFollowing_prIdx(prIdx)
                .stream().map(FollowDto::from).toList();
        return followingList;
    }

    public List<FollowDto> getFollowerList(Long prIdx) {
        List<FollowDto> followerList = followRepository.findByFollower_prIdx(prIdx)
                .stream().map(FollowDto::from).toList();
        return followerList;
    }

    public Follow follow(FollowRequest request) {
        Long followerIdx = request.follower();       // 팔로우 하는 회원의 idx
        Long followingIdx = request.following();     // 팔로우 당하는 회원의 idx
        return followRepository.save(request.toDto().toEntity());
    }

    @Transactional
    public Optional<Follow> unfollow(FollowRequest request) {
        Long followerIdx = request.follower();       // 팔로우 하는 회원의 idx
        Long followingIdx = request.following();     // 팔로우 당하는 회원의 idx
        return followRepository.deleteByFollower_prIdxAndFollowing_prIdx(followerIdx, followingIdx);
    }


}
