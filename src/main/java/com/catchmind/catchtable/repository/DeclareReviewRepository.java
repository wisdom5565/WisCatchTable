package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.DeclareReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeclareReviewRepository extends JpaRepository<DeclareReview, Long> {
    Page<DeclareReview> findAllByProfile_PrIdx(Long prIdx, Pageable pageable);
}
