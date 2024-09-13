package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Story;


import java.util.ArrayList;
import java.util.List;

public class StoryMapper {

    public static Story storyFromInputDtoToModel(StoryInputDto storyInputDto) {
        Story story = new Story();
        story.setTitle(storyInputDto.getTitle());
        story.setContent(storyInputDto.getContent());
        return story;
    }

    public static StoryOutputDto storyFromModelToOutputDto(Story story) {
        StoryOutputDto storyDto = new StoryOutputDto();
        storyDto.setId(story.getId());
        storyDto.setTitle(story.getTitle());
        storyDto.setContent(story.getContent());
        storyDto.setStatus(story.getStatus());
        storyDto.setPublishDate(story.getPublishDate());
        storyDto.setUsername(story.getAuthor().getUsername());
        storyDto.setAuthorFirstname(story.getAuthor().getFirstname());
        storyDto.setAuthorLastname(story.getAuthor().getLastname());
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

    public static Story storyOutputDtoToModel(StoryOutputDto outputDto) {
        Story story = new Story();
        story.setTitle(outputDto.getTitle());
        story.setContent(outputDto.getContent());
        return story;
    }


    public static List<Story> storyOutputListToModelList(List<StoryOutputDto> storyOutputDtos) {
        if(storyOutputDtos.isEmpty()) {
            throw new ResourceNotFoundException("No stories found.");
        }
        List<Story> storyList = new ArrayList<>();
        storyOutputDtos.forEach((dto) -> storyList.add(storyOutputDtoToModel(dto)));
        return storyList;
    }

}
