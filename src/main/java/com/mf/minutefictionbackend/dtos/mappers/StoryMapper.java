package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.models.Story;

import java.util.ArrayList;
import java.util.List;

public class StoryMapper {

    public static Story storyFromInputDtoToModel(StoryInputDto storyInputDto) {
        Story story = new Story();
        story.setTitle(storyInputDto.title);
        story.setContent(storyInputDto.content);

        return story;
    }

    public static StoryOutputDto storyFromModelToOutputDto(Story story) {
        StoryOutputDto storyOutputDto = new StoryOutputDto();
        storyOutputDto.setId(story.getId());
        storyOutputDto.setTitle(story.getTitle());
        storyOutputDto.setContent(story.getContent());
        storyOutputDto.setStatus(story.getStatus());
        storyOutputDto.setPublishDate(story.getPublishDate());
        storyOutputDto.setPublished(story.isPublished());

        return storyOutputDto;
    }

    public static List<StoryOutputDto> storyModelListToOutputList(List<Story> stories) {
        List<StoryOutputDto> storyOutputDtoList = new ArrayList<>();

        for(Story story : stories) {
            storyOutputDtoList.add(storyFromModelToOutputDto(story));
        }
        return storyOutputDtoList;
    }

}
