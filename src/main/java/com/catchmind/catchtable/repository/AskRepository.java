package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.Ask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskRepository extends JpaRepository<Ask, Long> {

    Ask findByAskIdx(Long AskIdx);
    Page<Ask> findAllByProfile_PrIdx(Long prIdx, Pageable pageable);

}
