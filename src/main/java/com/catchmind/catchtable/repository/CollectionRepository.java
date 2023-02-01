package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CollectionRepository extends JpaRepository<Collection,Long> {
    List<Collection> findAllByProfile_PrIdx(Long prIdx);
}
