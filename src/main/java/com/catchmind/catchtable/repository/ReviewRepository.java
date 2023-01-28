package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
