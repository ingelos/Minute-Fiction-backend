package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.ProfilePhotoInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.ProfilePhotoOutputDto;
import com.mf.minutefictionbackend.models.ProfilePhoto;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ProfilePhotoMapper {

    public static ProfilePhoto photoFromInputDtoToModel(ProfilePhotoInputDto profilePhotoInputDto) {
        ProfilePhoto profilePhoto = new ProfilePhoto();
        profilePhoto.setFileName(profilePhotoInputDto.getFileName());
        return profilePhoto;
    }

    public static ProfilePhotoOutputDto profilePhotoFromModelToOutputDto(ProfilePhoto profilePhoto) {
        if (profilePhoto == null) {
            return null;
        }

        ProfilePhotoOutputDto photoDto = new ProfilePhotoOutputDto();
        photoDto.setFileName(profilePhoto.getFileName());
        return photoDto;
    }


}
