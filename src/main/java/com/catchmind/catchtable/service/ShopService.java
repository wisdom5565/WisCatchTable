package com.catchmind.catchtable.service;

import com.catchmind.catchtable.dto.BistroInfoDto;
import com.catchmind.catchtable.dto.ReviewDto;
import com.catchmind.catchtable.dto.ReviewPhotoDto;
import com.catchmind.catchtable.dto.network.response.ReviewResponse;
import com.catchmind.catchtable.dto.network.response.ShopReviewResponse;
import com.catchmind.catchtable.repository.BistroInfoRepository;
import com.catchmind.catchtable.repository.PhotoRepository;
import com.catchmind.catchtable.repository.ReviewPhotoRepository;
import com.catchmind.catchtable.repository.ReviewRepository;
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
    // 식당 별 리뷰 리스트
    public Page<ReviewResponse> getBisNameReviews(Pageable pageable, String resaBisName, Long prIdx) {
        List<ReviewDto> bisNameReview = reviewRepository.findAllByResAdmin_ResaBisName(resaBisName).stream().map(ReviewDto::from).toList();
        List<ReviewDto> loginReview = reviewRepository.findAllByProfile_PrIdx(prIdx).stream().map(ReviewDto::from).toList();
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
    public List<ShopReviewResponse> getReview() {
        List<ShopReviewResponse> shopReviewResponseList = new ArrayList<>();
        List<BistroInfoDto> bistroInfoDtos = bistroInfoRepository.findAll().stream().map(BistroInfoDto::from).toList();
        List<ReviewDto> reviewDtos = new ArrayList<>();
        double totalScore = 0;
        Double avg = 0.0;
        for (BistroInfoDto resaBisName : bistroInfoDtos) {
            reviewDtos = reviewRepository.findAllByResAdmin_ResaBisName(resaBisName.resAdminDto().resaBisName()).stream().map(ReviewDto::from).toList();
            Long reviewCnt = reviewRepository.countByResAdmin_ResaBisName(resaBisName.resAdminDto().resaBisName());
            for (ReviewDto reviewDto : reviewDtos) {
                totalScore += reviewDto.revScore();
            }
            avg = (double) Math.round(totalScore / reviewDtos.size());

            if (avg.isInfinite()) {
                ShopReviewResponse response = new ShopReviewResponse(0.0, reviewCnt);
                shopReviewResponseList.add(response);
            } else {
                ShopReviewResponse response = new ShopReviewResponse(avg, reviewCnt);
                shopReviewResponseList.add(response);
            }
        }
        System.out.println(shopReviewResponseList);
        return shopReviewResponseList;
    }

}

