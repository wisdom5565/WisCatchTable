package com.catchmind.catchtable.dto.network.response;

import com.catchmind.catchtable.dto.BistroSaveDto;
import com.catchmind.catchtable.dto.MyCollectionDto;
import com.catchmind.catchtable.dto.PhotoDto;

import java.util.List;

public record SavedBistroResponse(
        List<PhotoDto> photo,
        List<MyCollectionDto> myCollectionDto
) {
}
