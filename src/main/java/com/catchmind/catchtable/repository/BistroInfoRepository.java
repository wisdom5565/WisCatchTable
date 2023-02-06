package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.BistroInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface BistroInfoRepository extends JpaRepository<BistroInfo, Long> {
    Optional<BistroInfo> findByResAdmin_ResaBisName(String resaBisName);
}
