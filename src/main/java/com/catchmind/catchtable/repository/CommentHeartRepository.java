package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.CommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    List<CommentHeart> findAllByReview_revIdx(Long Id);
}
