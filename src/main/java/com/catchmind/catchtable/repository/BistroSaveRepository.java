package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.BistroSave;
import com.catchmind.catchtable.domain.Profile;
import com.catchmind.catchtable.dto.BistroSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface BistroSaveRepository extends JpaRepository<BistroSave, Long> {
    List<BistroSave> findAllByProfile_PrIdx(Long prIdx);
}
