package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;

import java.util.ArrayList;
import java.util.List;

public class AuthorProfileMapper {

    public static AuthorProfile authorProfileFromInputDtoToModel(AuthorProfileInputDto authorProfileInputDto) {
        AuthorProfile authorProfile = new AuthorProfile();
        authorProfile.setUsername(authorProfileInputDto.getUsername());
        authorProfile.setFirstname(authorProfileInputDto.getFirstname());
        authorProfile.setLastname(authorProfileInputDto.getLastname());
        authorProfile.setBio(authorProfileInputDto.getBio());
        authorProfile.setDob(authorProfileInputDto.getDob());

        return authorProfile;
    }

    public static AuthorProfileOutputDto authorProfileFromModelToOutputDto(AuthorProfile authorProfile) {
        if(authorProfile == null) {
            return null;
        }

        AuthorProfileOutputDto authorProfileOutputDto = new AuthorProfileOutputDto();
        authorProfileOutputDto.setUsername(authorProfile.getUsername());
        authorProfileOutputDto.setFirstname(authorProfile.getFirstname());
        authorProfileOutputDto.setLastname(authorProfile.getLastname());
        authorProfileOutputDto.setBio(authorProfile.getBio());
        authorProfileOutputDto.setDob(authorProfile.getDob());

        return authorProfileOutputDto;
    }


    public static List<AuthorProfileOutputDto> authorProfileModelListToOutputList(List<AuthorProfile> profiles) {
        if(profiles.isEmpty()) {
            throw new ResourceNotFoundException("No author profiles found.");
        }

        List<AuthorProfileOutputDto> authorProfileOutputDtoList = new ArrayList<>();

        for(AuthorProfile authorProfile : profiles) {
            authorProfileOutputDtoList.add(authorProfileFromModelToOutputDto(authorProfile));
        }
        return authorProfileOutputDtoList;
    }


}
