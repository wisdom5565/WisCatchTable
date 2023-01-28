package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.BistroInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BistroInfoRepository extends JpaRepository<BistroInfo, Long> {
}
