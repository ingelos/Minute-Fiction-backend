package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.ThemeOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.Theme;


import java.util.ArrayList;
import java.util.List;

public class StoryMapper {

    public static Story storyFromInputDtoToModel(StoryInputDto storyInputDto, AuthorProfile authorProfile, Theme theme) {
        Story story = new Story();
        story.setTitle(storyInputDto.getTitle());
        story.setContent(storyInputDto.getContent());
        story.setStatus(StoryStatus.SUBMITTED);
        story.setPublishDate(null);
        story.setAuthorProfile(authorProfile);
        story.setTheme(theme);

        return story;
    }

    public static StoryOutputDto storyFromModelToOutputDto(Story story) {
        StoryOutputDto storyDto = new StoryOutputDto();
        storyDto.setId(story.getId());
        storyDto.setTitle(story.getTitle());
        storyDto.setContent(story.getContent());
        storyDto.setStatus(story.getStatus());
        storyDto.setPublishDate(story.getPublishDate());

        if(story.getAuthorProfile() != null) {
            AuthorProfileOutputDto dto = new AuthorProfileOutputDto();
            dto.setUsername(story.getAuthorProfile().getUsername());
            dto.setFirstname(story.getAuthorProfile().getFirstname());
            dto.setLastname(story.getAuthorProfile().getLastname());
            dto.setBio(story.getAuthorProfile().getBio());
            dto.setDob(story.getAuthorProfile().getDob());

            storyDto.setAuthorProfile(dto);
        }

        if(story.getTheme() != null) {
            ThemeOutputDto themeDto = new ThemeOutputDto();
            themeDto.setId(story.getTheme().getId());
            themeDto.setName(story.getTheme().getName());
            themeDto.setDescription(story.getTheme().getDescription());

            storyDto.setTheme(themeDto);
        }


        return storyDto;
    }

    public static List<StoryOutputDto> storyModelListToOutputList(List<Story> stories) {
        List<StoryOutputDto> storyOutputDtoList = new ArrayList<>();

        for(Story story : stories) {
            storyOutputDtoList.add(storyFromModelToOutputDto(story));
        }
        return storyOutputDtoList;
    }

}
