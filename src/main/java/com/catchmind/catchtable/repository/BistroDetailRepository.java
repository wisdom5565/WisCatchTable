package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.BistroDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BistroDetailRepository extends JpaRepository<BistroDetail,Long> {
    Optional<BistroDetail> findByResAdmin_ResaBisName(String resaBisName);
    List<BistroDetail> findTop8By();
}
