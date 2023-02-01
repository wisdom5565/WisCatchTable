package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProfile_PrIdx(Long prIdx);
//    @EntityGraph(attributePaths = {"reviewPhotos"})
//    List<Review> findAllByRevIdxAndProfile_PrIdx(Long revIdx, Long prIdx);
//    Review findAllByReserve_ResIdx(Long resIdx);
}
