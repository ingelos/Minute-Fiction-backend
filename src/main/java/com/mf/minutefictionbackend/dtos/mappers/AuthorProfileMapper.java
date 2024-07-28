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

        AuthorProfileOutputDto dto = new AuthorProfileOutputDto();
        dto.setId(authorProfile.getId());
        dto.setFirstname(authorProfile.getFirstname());
        dto.setLastname(authorProfile.getLastname());
        dto.setBio(authorProfile.getBio());
        dto.setDob(authorProfile.getDob());

        if(authorProfile.getStories() != null) {
            List<String> storyTitles = new ArrayList<>();
            authorProfile.getStories().forEach(story -> storyTitles.add(story.getTitle()));
            dto.setStoryTitles(storyTitles);
        } else {
            dto.setStoryTitles(new ArrayList<>());
        }

        return dto;
    }


    public static List<AuthorProfileOutputDto> authorProfileModelListToOutputList(List<AuthorProfile> profiles) {
        if(profiles.isEmpty()) {
            throw new ResourceNotFoundException("No author profiles found.");
        }
        List<AuthorProfileOutputDto> authorProfileOutputDtoList = new ArrayList<>();
        profiles.forEach((profile) -> authorProfileOutputDtoList.add(authorProfileFromModelToOutputDto(profile)));
        return authorProfileOutputDtoList;
    }


}
