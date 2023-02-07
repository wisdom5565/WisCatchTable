package com.catchmind.catchtable.service;

import com.catchmind.catchtable.domain.BistroSave;
import com.catchmind.catchtable.domain.MyCollection;
import com.catchmind.catchtable.domain.Profile;
import com.catchmind.catchtable.dto.BistroSaveDto;
import com.catchmind.catchtable.dto.MyCollectionDto;
import com.catchmind.catchtable.dto.ProfileDto;
import com.catchmind.catchtable.dto.network.request.MyCollectionRequest;
import com.catchmind.catchtable.repository.BistroSaveRepository;
import com.catchmind.catchtable.repository.MyCollectionRepository;
import com.catchmind.catchtable.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileLogicService {
    private final ProfileRepository profileRepository;
    private final BistroSaveRepository bistroSaveRepository;
    private final MyCollectionRepository myCollectionRepository;
    private final PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();


    public boolean login(String prHp, String prUserpw){
        ProfileDto profileDto =  profileRepository.findByPrHp(prHp).map(ProfileDto::from).orElseThrow();
        boolean isTrue = passwordEncoder.matches(prUserpw,profileDto.prUserpw());
        System.out.println(isTrue);
        return isTrue;
    }
    public ProfileDto getProfileElements(Long prIdx){
        return profileRepository.findById(prIdx)
                .map(ProfileDto::from)
                .orElseThrow(() -> new EntityNotFoundException("유저가 없는데여 - prIdx :" + prIdx));
    }

    public List<BistroSaveDto> getList(Long prIdx){
        return bistroSaveRepository.findAllByProfile_PrIdx(prIdx).stream().map(BistroSaveDto::from).collect(Collectors.toList());
    }
    public List<BistroSaveDto> getSaveList(Long colIdx){
        return bistroSaveRepository.findAllByColIdx(colIdx).stream().map(BistroSaveDto::from).collect(Collectors.toList());
    }
    public Profile saveMember(Profile profile) {
        System.out.println(profile);
        return profileRepository.save(profile);
    }
    public void updateProfile(Long prIdx, ProfileDto profileDto) {
        try {
            System.out.println(profileDto);
            Optional<Profile> profile = profileRepository.findById(prIdx);
            profile.ifPresent(
                    member -> {
                        if(profileDto.prUserpw() != null) {member.setPrUserpw(passwordEncoder.encode(profileDto.prUserpw()));}
                        if(profileDto.prHp() != null) {member.setPrHp(profileDto.prHp());}
                        if(profileDto.prGender() != null) {member.setPrGender(profileDto.prGender());}
                        if(profileDto.prIntro() != null) {member.setPrIntro(profileDto.prIntro());}
                        if(profileDto.prNick() != null) {member.setPrNick(profileDto.prNick());}
                        if(profileDto.prRegion() != null) {member.setPrRegion(profileDto.prRegion());}
                        if(profileDto.prName() != null) {member.setPrName(profileDto.prName());}
                    }
            );
        } catch (EntityNotFoundException e) {
            log.warn("업데이트 실패..!!!, 공지사항을 찾을 수 없음  - exNoticeDTO {} ", profileDto);
        }
    }
    //    회원탈퇴
    public void delete(Long prIdx){
        profileRepository.deleteById(prIdx);
    }
    //    컬렉션 삭제
    public void delMyCollection(Long colIdx){
        myCollectionRepository.deleteById(colIdx);
    }
    public void delRes(Long saveIdx){
        bistroSaveRepository.deleteById(saveIdx);
    }
    public void createCollection(MyCollectionRequest request){
        System.out.println(request);
        System.out.println(request.toDto());
        System.out.println(request.toDto().toEntity());
        myCollectionRepository.save(request.toDto().toEntity());
    }
    public List<MyCollectionDto> getColList(Long prIdx){
        return myCollectionRepository.findAllByProfile_PrIdx(prIdx).stream().map(MyCollectionDto::from).collect(Collectors.toList());
    }

    public MyCollectionDto getMyCollectionElements(Long colIdx){
        return myCollectionRepository.findById(colIdx)
                .map(MyCollectionDto::from)
                .orElseThrow(() -> new EntityNotFoundException("유저가 없는데여 - prIdx :" + colIdx));
    }

    public void updateMyCollection(Long colIdx, MyCollectionDto myCollectionDto) {
        try {
            System.out.println(myCollectionDto);
            Optional<MyCollection> myCollection = myCollectionRepository.findById(colIdx);
            myCollection.ifPresent(
                    collection -> {
                        if(myCollectionDto.colTitle() != null) {collection.setColTitle(myCollectionDto.colTitle());}
                        if(myCollectionDto.colContent() != null) {collection.setColContent(myCollectionDto.colTitle());}
                        if(myCollectionDto.colLock() != true) {collection.setColLock(false);}
                    }
            );
        } catch (EntityNotFoundException e) {
            log.warn("업데이트 실패..!!!, 공지사항을 찾을 수 없음  - myCollectionDTO {} ", myCollectionDto);
        }
    }

    public MyCollection updateMyCollectionSave(Long colIdx, String bisNames) {
        MyCollection myCollection = myCollectionRepository.findById(colIdx).orElse(null);
        myCollection.setBisNames(bisNames);
        return myCollectionRepository.save(myCollection);
    }
    @Transactional
    public void updateBistroSave(Long colIdx,String bisNames) {
        String[] saveIdx = bisNames.split(",");
        for(String idx : saveIdx) {
            System.out.println(idx);
            BistroSave bistroSave = bistroSaveRepository.findByResAdmin_ResaBisName(idx);
            System.out.println("🐒" + bistroSave);
            bistroSave.setColIdx(colIdx);
//            bistroSaveRepository.save(bistroSave);
        }
    }
    public BistroSave delCollectionSave(Long saveIdx){
        BistroSave bistroSave = bistroSaveRepository.findById(saveIdx).orElse(null);
        bistroSave.setColIdx(null);
        return bistroSaveRepository.save(bistroSave);
    }


    public Optional<Profile> checkNick(String prNick) {
        Optional<Profile> profile = profileRepository.findByPrNick(prNick);
        return profile;
    }
}
