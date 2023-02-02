package com.catchmind.catchtable.dto;

import com.catchmind.catchtable.domain.MyCollection;

public record MyCollectionDto(
      Long colIdx,
      String colTitle,
      String colContent,
      boolean colLock,
      ProfileDto profileDto,
      String bisIdx
) {
    public static MyCollectionDto from(MyCollection collection) {
        return new MyCollectionDto(
                collection.getColIdx(),
                collection.getColTitle(),
                collection.getColContent(),
                collection.isColLock(),
                ProfileDto.from(collection.getProfile()),
                collection.getBisIdx()
        );

    }
}
