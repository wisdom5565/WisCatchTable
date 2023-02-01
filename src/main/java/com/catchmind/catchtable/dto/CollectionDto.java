package com.catchmind.catchtable.dto;

import com.catchmind.catchtable.domain.Collection;

public record CollectionDto(
      Long colIdx,
      String colTitle,
      String colContent,
      boolean colLock,
      ProfileDto profileDto,
      String bisIdx
) {
    public static CollectionDto from(Collection collection) {
        return new CollectionDto(
                collection.getColIdx(),
                collection.getColTitle(),
                collection.getColContent(),
                collection.isColLock(),
                ProfileDto.from(collection.getProfile()),
                collection.getBisIdx()
        );

    }
}
