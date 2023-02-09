package com.catchmind.catchtable.service;

import com.catchmind.catchtable.dto.BistroInfoDto;
import com.catchmind.catchtable.dto.ReviewDto;
import com.catchmind.catchtable.dto.network.response.ShopReviewResponse;
import com.catchmind.catchtable.repository.BistroInfoRepository;
import com.catchmind.catchtable.repository.PhotoRepository;
import com.catchmind.catchtable.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

