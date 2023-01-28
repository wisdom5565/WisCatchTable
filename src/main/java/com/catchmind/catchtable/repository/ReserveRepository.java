package com.catchmind.catchtable.repository;

import com.catchmind.catchtable.domain.Reserve;
import com.catchmind.catchtable.domain.type.ReservationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    List<Reserve> findAllByresStatus(ReservationType resStatus);

}
