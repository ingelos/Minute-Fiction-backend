package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.dtos.ProfilePhotoDto;
import com.mf.minutefictionbackend.models.ProfilePhoto;

public class ProfilePhotoMapper {

    public static ProfilePhotoDto profilePhotoFromModelToDto(ProfilePhoto profilePhoto) {
        if (profilePhoto == null) {
            return null;
        }
        ProfilePhotoDto photoDto = new ProfilePhotoDto();
        photoDto.setFileName(profilePhoto.getFileName());
        return photoDto;
    }


}
