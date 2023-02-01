package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.BistroSave;
import com.catchmind.catchtable.domain.Profile;
import com.catchmind.catchtable.dto.ProfileDto;
import com.jayway.jsonpath.JsonPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByPrHp(String prHp);

    Optional<Profile> findByPrHpAndPrUserpw(String prHp, String prUserpw);

}
