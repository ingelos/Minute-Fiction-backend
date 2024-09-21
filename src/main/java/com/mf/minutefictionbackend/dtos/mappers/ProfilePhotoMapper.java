package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.outputDtos.ProfilePhotoOutputDto;
import com.mf.minutefictionbackend.models.ProfilePhoto;

public class ProfilePhotoMapper {

    public static ProfilePhotoOutputDto profilePhotoFromModelToOutputDto(ProfilePhoto profilePhoto) {
        if (profilePhoto == null) {
            return null;
        }
        ProfilePhotoOutputDto photoDto = new ProfilePhotoOutputDto();
        photoDto.setFileName(profilePhoto.getFileName());
        return photoDto;
    }


}
