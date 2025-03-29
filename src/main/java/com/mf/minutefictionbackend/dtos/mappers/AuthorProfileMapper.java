package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.AuthorProfile;

import java.util.ArrayList;
import java.util.List;

public class AuthorProfileMapper {

    public static AuthorProfile authorProfileFromInputDtoToModel(AuthorProfileInputDto authorProfileInputDto) {
        if(authorProfileInputDto == null) {
            return null;
        }
        AuthorProfile authorProfile = new AuthorProfile();
        authorProfile.setFirstname(authorProfileInputDto.getFirstname());
        authorProfile.setLastname(authorProfileInputDto.getLastname());
        authorProfile.setBio(authorProfileInputDto.getBio());
        authorProfile.setDob(authorProfileInputDto.getDob());
        return authorProfile;
    }


    public static AuthorProfileOutputDto authorProfileFromModelToOutputDto(AuthorProfile authorProfile) {
        if (authorProfile == null) {
            return null;
        }
        AuthorProfileOutputDto dto = new AuthorProfileOutputDto();
        dto.setUsername(authorProfile.getUsername());
        dto.setFirstname(authorProfile.getFirstname());
        dto.setLastname(authorProfile.getLastname());
        dto.setBio(authorProfile.getBio());
        dto.setDob(authorProfile.getDob());

        if (authorProfile.getProfilePhoto() != null) {
            dto.setProfilePhoto(ProfilePhotoMapper.profilePhotoFromModelToDto(authorProfile.getProfilePhoto()));
        }
        return dto;
    }


    public static List<AuthorProfileOutputDto> authorProfileModelListToOutputList(List<AuthorProfile> profiles) {
        if (profiles.isEmpty()) {
            throw new ResourceNotFoundException("No author profiles found.");
        }
        List<AuthorProfileOutputDto> authorProfileOutputDtoList = new ArrayList<>();
        profiles.forEach((profile) -> authorProfileOutputDtoList.add(authorProfileFromModelToOutputDto(profile)));
        return authorProfileOutputDtoList;
    }


}
