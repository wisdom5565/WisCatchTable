package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.BistroSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BistroSaveRepository extends JpaRepository<BistroSave, Long> {

}
