package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
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

        storyDto.setAuthorUsername(story.getAuthorProfile().getUsername());
        storyDto.setThemeName(story.getTheme().getName());


        return storyDto;
    }

    public static List<StoryOutputDto> storyModelListToOutputList(List<Story> stories) {
        if(stories.isEmpty()) {
            throw new ResourceNotFoundException("No stories found.");
        }

        List<StoryOutputDto> storyOutputDtoList = new ArrayList<>();
        stories.forEach((story) -> storyOutputDtoList.add(storyFromModelToOutputDto(story)));
        return storyOutputDtoList;
    }

}
