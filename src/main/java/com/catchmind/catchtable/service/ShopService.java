package com.catchmind.catchtable.service;

import com.catchmind.catchtable.dto.BistroDetailDto;
import com.catchmind.catchtable.dto.BistroSaveDto;
import com.catchmind.catchtable.dto.ReviewDto;
import com.catchmind.catchtable.dto.ReviewPhotoDto;
import com.catchmind.catchtable.dto.network.response.ReviewResponse;
import com.catchmind.catchtable.dto.network.response.ShopListResponse;
import com.catchmind.catchtable.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShopService {
    private final ReviewRepository reviewRepository;
    private final BistroInfoRepository bistroInfoRepository;
    private final PhotoRepository photoRepository;
    private final ReviewPhotoRepository reviewPhotoRepository;
    private final BistroSaveRepository bistroSaveRepository;
    private final BistroDetailRepository bistroDetailRepository;

    // 식당 별 리뷰 리스트
    public Page<ReviewResponse> getBisNameReviews(Pageable pageable, String resaBisName, Long prIdx) {
        List<ReviewDto> bisNameReview = reviewRepository.findAllByResAdmin_ResaBisName(resaBisName).stream().map(ReviewDto::from).toList();
        List<ReviewDto> loginReview = reviewRepository.findAllByProfile_PrIdx(prIdx, Sort.by(Sort.Direction.DESC, "revIdx")).stream().map(ReviewDto::from).toList();
        List<ReviewResponse> reviewList = new ArrayList<>();
        List<ReviewPhotoDto> photoDtos = reviewPhotoRepository.findAll().stream().map(ReviewPhotoDto::from).toList();

        // 리뷰 별 사진 리스트
        for (int i = 0; i < bisNameReview.size(); i++) {
            List<ReviewPhotoDto> photoList = new ArrayList<>();
            for (int j = 0; j < photoDtos.size(); j++) {
                if (bisNameReview.get(i).revIdx() == photoDtos.get(j).reviewDto().revIdx()) {
                    String orgNm = photoDtos.get(j).orgNm();
                    String savedNm = photoDtos.get(j).savedNm();
                    String savedPath = photoDtos.get(j).savedPath();
                    ReviewDto reviewDto = photoDtos.get(j).reviewDto();
                    ReviewPhotoDto real = ReviewPhotoDto.of(orgNm, savedNm, savedPath, reviewDto);
                    photoList.add(real);
                }
            }
            boolean isReview = false;
            for (ReviewDto login : loginReview) {
                isReview = bisNameReview.get(i).revIdx().equals(login.revIdx());
            }
            if (photoList.isEmpty() || bisNameReview.get(i).updateDate() == null) {
                ReviewResponse response = new ReviewResponse(bisNameReview.get(i).revIdx(), bisNameReview.get(i).profileDto(), bisNameReview.get(i).revContent(), bisNameReview.get(i).revScore(),
                        bisNameReview.get(i).resAdminDto(), null, bisNameReview.get(i).reserveDto().resIdx(),
                        bisNameReview.get(i).regDate(), null, isReview);
                reviewList.add(response);
            } else {
                ReviewResponse response = new ReviewResponse(bisNameReview.get(i).revIdx(), bisNameReview.get(i).profileDto(), bisNameReview.get(i).revContent(), bisNameReview.get(i).revScore(),
                        bisNameReview.get(i).resAdminDto(), photoList, bisNameReview.get(i).reserveDto().resIdx(),
                        bisNameReview.get(i).regDate()
                        , bisNameReview.get(i).updateDate(), isReview);
                reviewList.add(response);
            }
        }
        System.out.println("검증 후 리뷰 리스트 : " + reviewList);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), reviewList.size());
        PageImpl<ReviewResponse> reviewResponsePage = new PageImpl<>(reviewList.subList(start, end), pageable, reviewList.size());
        return reviewResponsePage;
    }

    public Page<ReviewResponse> getBisNameReview(Pageable pageable, String resaBisName) {
        List<ReviewDto> bisNameReview = reviewRepository.findAllByResAdmin_ResaBisName(resaBisName).stream().map(ReviewDto::from).toList();
        List<ReviewResponse> reviewList = new ArrayList<>();
        List<ReviewPhotoDto> photoDtos = reviewPhotoRepository.findAll().stream().map(ReviewPhotoDto::from).toList();

        // 리뷰 별 사진 리스트
        for (int i = 0; i < bisNameReview.size(); i++) {
            List<ReviewPhotoDto> photoList = new ArrayList<>();
            for (int j = 0; j < photoDtos.size(); j++) {
                if (bisNameReview.get(i).revIdx() == photoDtos.get(j).reviewDto().revIdx()) {
                    String orgNm = photoDtos.get(j).orgNm();
                    String savedNm = photoDtos.get(j).savedNm();
                    String savedPath = photoDtos.get(j).savedPath();
                    ReviewDto reviewDto = photoDtos.get(j).reviewDto();
                    ReviewPhotoDto real = ReviewPhotoDto.of(orgNm, savedNm, savedPath, reviewDto);
                    photoList.add(real);
                }
            }
            boolean isReview = false;
            if (photoList.isEmpty() || bisNameReview.get(i).updateDate() == null) {
                ReviewResponse response = new ReviewResponse(bisNameReview.get(i).revIdx(), bisNameReview.get(i).profileDto(), bisNameReview.get(i).revContent(), bisNameReview.get(i).revScore(),
                        bisNameReview.get(i).resAdminDto(), null, bisNameReview.get(i).reserveDto().resIdx(),
                        bisNameReview.get(i).regDate(), null, isReview);
                reviewList.add(response);
            } else {
                ReviewResponse response = new ReviewResponse(bisNameReview.get(i).revIdx(), bisNameReview.get(i).profileDto(), bisNameReview.get(i).revContent(), bisNameReview.get(i).revScore(),
                        bisNameReview.get(i).resAdminDto(), photoList, bisNameReview.get(i).reserveDto().resIdx(),
                        bisNameReview.get(i).regDate()
                        , bisNameReview.get(i).updateDate(), isReview);
                reviewList.add(response);
            }
        }
        System.out.println("검증 후 리뷰 리스트 : " + reviewList);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), reviewList.size());
        PageImpl<ReviewResponse> reviewResponsePage = new PageImpl<>(reviewList.subList(start, end), pageable, reviewList.size());
        return reviewResponsePage;
    }

    // 식당 전체리스트
    public Page<ShopListResponse> shopList(Pageable pageable, Long prIdx) {
        List<ShopListResponse> shopListResponses = new ArrayList<>();
        List<BistroDetailDto> bistroDetailDtos = bistroDetailRepository.findAll().stream().map(BistroDetailDto::from).toList();
        if (prIdx == null) {
            List<ReviewDto> reviewDtos = new ArrayList<>();

            double totalScore = 0;
            double avg = 0;
            for (BistroDetailDto bistroDetailDto : bistroDetailDtos) {
                reviewDtos = reviewRepository.findAllByResAdmin_ResaBisName(bistroDetailDto.resAdminDto().resaBisName()).stream().map(ReviewDto::from).toList();
                Long reviewCnt = reviewRepository.countByResAdmin_ResaBisName(bistroDetailDto.resAdminDto().resaBisName());
                if (reviewDtos.isEmpty()) {
                    ShopListResponse response = new ShopListResponse("0.0", reviewCnt, bistroDetailDto.resAdminDto().resaBisName(), bistroDetailDto, bistroDetailDto.bistroInfoDto().photoDto(), false);
                    shopListResponses.add(response);
                } else {
                    for (ReviewDto reviewDto : reviewDtos) {
                        System.out.println(reviewDto.revScore());
                        totalScore += reviewDto.revScore();
                        System.out.println(totalScore);
                    }
                    System.out.println(avg = totalScore / reviewDtos.size());
                    ShopListResponse response = new ShopListResponse(String.format("%.1f", avg), reviewCnt, bistroDetailDto.resAdminDto().resaBisName(), bistroDetailDto, bistroDetailDto.bistroInfoDto().photoDto(), false);
                    shopListResponses.add(response);
                }
            }
        } else {
            List<BistroSaveDto> bistroSaveDtos = bistroSaveRepository.findAllByProfile_PrIdx(prIdx).stream().map(BistroSaveDto::from).toList();
            List<ReviewDto> reviewDtos = new ArrayList<>();

            double totalScore = 0;
            double avg = 0;
            for (BistroDetailDto bistroDetailDto : bistroDetailDtos) {
                reviewDtos = reviewRepository.findAllByResAdmin_ResaBisName(bistroDetailDto.resAdminDto().resaBisName()).stream().map(ReviewDto::from).toList();
                Long reviewCnt = reviewRepository.countByResAdmin_ResaBisName(bistroDetailDto.resAdminDto().resaBisName());
                boolean isSaved = false;
                for (BistroSaveDto bistroSaveDto : bistroSaveDtos) {
                    if (bistroDetailDto.bdIdx() == bistroSaveDto.bistroDetailDto().bdIdx()) {
                        isSaved = true;
                    }
                }
                if (reviewDtos.isEmpty()) {
                    ShopListResponse response = new ShopListResponse("0.0", reviewCnt, bistroDetailDto.resAdminDto().resaBisName(), bistroDetailDto, bistroDetailDto.bistroInfoDto().photoDto(), isSaved);
                    shopListResponses.add(response);
                } else {
                    for (ReviewDto reviewDto : reviewDtos) {
                        System.out.println(reviewDto.revScore());
                        totalScore += reviewDto.revScore();
                        System.out.println(totalScore);
                    }
                    System.out.println(avg = totalScore / reviewDtos.size());
                    ShopListResponse response = new ShopListResponse(String.format("%.1f", avg), reviewCnt, bistroDetailDto.resAdminDto().resaBisName(), bistroDetailDto, bistroDetailDto.bistroInfoDto().photoDto(), isSaved);
                    shopListResponses.add(response);
                }
            }
        }
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), shopListResponses.size());
        PageImpl<ShopListResponse> shopListResponsePage = new PageImpl<>(shopListResponses.subList(start, end), pageable, shopListResponses.size());
        return shopListResponsePage;
    }

}

