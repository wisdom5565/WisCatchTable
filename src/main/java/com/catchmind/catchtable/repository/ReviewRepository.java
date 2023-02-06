package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProfile_PrIdx(Long prIdx);

    Page<Review> findAllByResAdmin_ResaBisName(String resaBisName, Pageable pageable);
    List<Review> findAllByResAdmin_ResaBisName(String resaBisName);

    Review findByRevIdx(Long revIdx);

}
