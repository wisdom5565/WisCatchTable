package com.catchmind.catchtable.service;

import com.catchmind.catchtable.domain.BistroSave;
import com.catchmind.catchtable.domain.Profile;
import com.catchmind.catchtable.dto.BistroSaveDto;
import com.catchmind.catchtable.dto.ProfileDto;
import com.catchmind.catchtable.repository.BistroSaveRepository;
import com.catchmind.catchtable.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Transactional
@Service
@RequiredArgsConstructor
public class ProfileLogicService {
    private final ProfileRepository profileRepository;
    private final BistroSaveRepository bistroSaveRepository;


    public ProfileDto login(String prHp, String prUserpw){
        return profileRepository.findByPrHpAndPrUserpw(prHp,prUserpw).map(ProfileDto::from).orElseThrow(
                () -> new EntityNotFoundException("회원이없습니다!"));
    }
    public ProfileDto getProfileElements(Long prIdx){
        return profileRepository.findById(prIdx)
                .map(ProfileDto::from)
                .orElseThrow(() -> new EntityNotFoundException("유저가 없는데여 - prIdx :" + prIdx));
    }

    public List<BistroSaveDto> getList(Long prIdx){
        return bistroSaveRepository.findAllByProfile_PrIdx(prIdx).stream().map(BistroSaveDto::from).collect(Collectors.toList());
    }

    public Profile saveMember(Profile profile) {
        System.out.println(profile);
        return profileRepository.save(profile);
    }



}
