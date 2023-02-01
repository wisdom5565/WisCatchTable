package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.ReviewHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReviewHeartRepository extends JpaRepository<ReviewHeart,Long> {
    List<ReviewHeart> findAllByReview_revIdx(Long Id);
}
