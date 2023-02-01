package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.ResAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface ResAdminRepository extends JpaRepository<ResAdmin, String> {

}
