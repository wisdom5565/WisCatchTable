package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.MyCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MyCollectionRepository extends JpaRepository<MyCollection,Long> {
    Page<MyCollection> findAllByProfile_PrIdxAndColLock(Long prIdx,boolean colLock, Pageable pageable);
    List<MyCollection> findAllByProfile_PrIdx(Long prIdx);
}
