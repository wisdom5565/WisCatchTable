package com.catchmind.catchtable.service;

import com.catchmind.catchtable.dto.ProfileDto;
import com.catchmind.catchtable.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ProfileLogicService{
    private final ProfileRepository profileRepository;

    public ProfileDto login(String prHp, String prUserpw){
        return profileRepository.findByPrHpAndPrUserpw(prHp,prUserpw).map(ProfileDto::from).orElseThrow(
                () -> new EntityNotFoundException("회원이없습니다!"));
    }
}
