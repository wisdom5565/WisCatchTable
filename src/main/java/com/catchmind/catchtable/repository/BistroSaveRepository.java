package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.BistroSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface BistroSaveRepository extends JpaRepository<BistroSave, Long> {
    List<BistroSave> findAllByProfile_PrIdx(Long prIdx);
    List<BistroSave> findAllByColIdx(Long colIdx);
    BistroSave findByResAdmin_ResaBisName(String resaBisName);
}
