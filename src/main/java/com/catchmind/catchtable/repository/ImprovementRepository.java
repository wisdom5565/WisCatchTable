package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.Improvement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImprovementRepository extends JpaRepository<Improvement, Long> {
    List<Improvement> findAllByProfile_PrIdx(Long prIdx);
}
