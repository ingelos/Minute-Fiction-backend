package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.ThemeOutputDto;
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
        story.setStatus("submitted");
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

        AuthorProfile authorProfile = story.getAuthorProfile();
        if(authorProfile != null) {
            AuthorProfileOutputDto authorProfileDto = new AuthorProfileOutputDto();
            authorProfileDto.setUsername(story.getAuthorProfile().getUsername());
            authorProfileDto.setFirstname(story.getAuthorProfile().getFirstname());
            authorProfileDto.setLastname(story.getAuthorProfile().getLastname());
            authorProfileDto.setBio(story.getAuthorProfile().getBio());
            authorProfileDto.setDob(story.getAuthorProfile().getDob());

            storyDto.setAuthorProfile(authorProfileDto);
        }

        Theme theme = story.getTheme();
        if(theme != null) {
            ThemeOutputDto themeDto = new ThemeOutputDto();
            themeDto.setId(theme.getId());
            themeDto.setName(theme.getName());
            themeDto.setDescription(theme.getDescription());

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
