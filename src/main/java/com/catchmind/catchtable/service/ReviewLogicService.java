package com.catchmind.catchtable.service;


import com.catchmind.catchtable.dto.ReviewDto;
import com.catchmind.catchtable.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewLogicService {
    private final ReviewRepository reviewRepository;
    public List<ReviewDto> reviewList(String resaBisName){
        return reviewRepository.findAllByResAdmin_ResaBisName(resaBisName).stream().map(ReviewDto::from).toList();
    }

    public Page<ReviewDto> reviewList(String resaBisName, PageRequest pageRequest){
        Page<ReviewDto> reviewDtos = reviewRepository.findAllByResAdmin_ResaBisName(resaBisName,pageRequest).map(ReviewDto::from);
        return reviewDtos;
    }
    //가게 이름으로 찾은 리뷰정보가

    public List<ReviewDto> reviewList(){
        return reviewRepository.findAll().stream().map(ReviewDto::from).toList();
    }

}
