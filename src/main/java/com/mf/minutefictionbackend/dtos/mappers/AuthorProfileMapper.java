package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;

import java.util.ArrayList;
import java.util.List;

public class AuthorProfileMapper {

    public static AuthorProfile authorProfileFromInputDtoToModel(AuthorProfileInputDto authorProfileInputDto) {
        AuthorProfile authorProfile = new AuthorProfile();
        authorProfile.setUsername(authorProfileInputDto.username);
        authorProfile.setFirstname(authorProfileInputDto.firstname);
        authorProfile.setLastname(authorProfileInputDto.lastname);
        authorProfile.setBio(authorProfileInputDto.bio);
        authorProfile.setDob(authorProfileInputDto.dob);

        return authorProfile;
    }

    public static AuthorProfileOutputDto authorProfileFromModelToOutputDto(AuthorProfile authorProfile) {
        AuthorProfileOutputDto authorProfileOutputDto = new AuthorProfileOutputDto();

        authorProfileOutputDto.setUsername(authorProfile.getUsername());
        authorProfileOutputDto.setFirstname(authorProfile.getFirstname());
        authorProfileOutputDto.setLastname(authorProfile.getLastname());
        authorProfileOutputDto.setBio(authorProfile.getBio());
        authorProfileOutputDto.setDob(authorProfile.getDob());

        return authorProfileOutputDto;
    }


    public static List<AuthorProfileOutputDto> authorProfileModelListToOutputList(List<AuthorProfile> profiles) {
        List<AuthorProfileOutputDto> authorProfileOutputDtoList = new ArrayList<>();

        profiles.forEach((profile) -> authorProfileOutputDtoList.add(authorProfileFromModelToOutputDto(profile)));
        return authorProfileOutputDtoList;
    }


}
