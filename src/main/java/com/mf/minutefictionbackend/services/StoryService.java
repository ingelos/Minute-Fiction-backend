package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.mappers.StoryMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.repositories.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mf.minutefictionbackend.dtos.mappers.StoryMapper.storyFromModelToOutputDto;
import static com.mf.minutefictionbackend.dtos.mappers.StoryMapper.storyModelListToOutputList;

@Service
public class StoryService {

    private final StoryRepository storyRepository;

    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }


    public StoryOutputDto createStory(StoryInputDto storyInputDto) {
        Story story = storyRepository.save(StoryMapper.storyFromInputDtoToModel(storyInputDto));
        return storyFromModelToOutputDto(story);
    }

    public List<StoryOutputDto> getAllStories() {
        List<Story> allStories = storyRepository.findAll();
        return storyModelListToOutputList(allStories);

    }

    public StoryOutputDto getStoryById(Long id) {
        Optional<Story> optionalStory = storyRepository.findById(id);
        if(optionalStory.isPresent()) {
            return storyFromModelToOutputDto(optionalStory.get());
        } else throw new ResourceNotFoundException("No story found with id " + id);
    }

    public StoryOutputDto deleteStory(Long id) {
        Optional<Story> optionalStory = storyRepository.findById(id);
        if(optionalStory.isPresent()) {
            Story story = optionalStory.get();
            storyRepository.delete(story);
            return storyFromModelToOutputDto(story);
        } else throw new ResourceNotFoundException("No story found with id " + id);
    }
}
