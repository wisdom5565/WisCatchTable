package com.catchmind.catchtable.service;

import com.catchmind.catchtable.domain.BistroSave;
import com.catchmind.catchtable.dto.network.request.BistroSaveRequest;
import com.catchmind.catchtable.repository.BistroDetailRepository;
import com.catchmind.catchtable.repository.BistroSaveRepository;
import com.catchmind.catchtable.repository.ProfileRepository;
import com.catchmind.catchtable.repository.ResAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BistroSaveService {
    private final BistroSaveRepository bistroSaveRepository;
    private final BistroDetailRepository bistroDetailRepository;
    private final ProfileRepository profileRepository;
    private final ResAdminRepository resAdminRepository;


    // 북마크 저장 create
    public void newBookmark(BistroSaveRequest request) {
        BistroSave bistroSave= new BistroSave(request.prIdx(), request.resaBisName());
        bistroSaveRepository.save(bistroSave);

    }

    // 북마크 삭제
    @Transactional
    public void delBookmark(BistroSaveRequest request){
        System.out.println("서비스 진입");
        bistroSaveRepository.deleteByProfile_prIdxAndResAdmin_ResaBisName(request.prIdx(), request.resaBisName());
//        BistroSave bistroDelete= new BistroSave(request.prIdx(),request.resaBisName());
//        bistroSaveRepository.delete(bistroDelete);
    }

}
