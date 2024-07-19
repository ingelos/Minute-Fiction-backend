package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.mappers.StoryMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.repositories.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StoryService {

    private final StoryRepository storyRepository;

    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }


    public StoryOutputDto createStory(StoryInputDto storyInputDto) {
        Story story = storyRepository.save(StoryMapper.storyFromInputDtoToModel(storyInputDto));
        return StoryMapper.storyFromModelToOutputDto(story);
    }

    public List<StoryOutputDto> getAllStories() {
        List<Story> allStories = storyRepository.findAll();
        return StoryMapper.storyModelListToOutputList(allStories);
    }

    public StoryOutputDto getStoryById(Long id) {
        Optional<Story> optionalStory = storyRepository.findById(id);
        if(optionalStory.isPresent()) {
            return StoryMapper.storyFromModelToOutputDto(optionalStory.get());
        } else throw new ResourceNotFoundException("No story found with id " + id);
    }

    public void deleteStoryById(Long id) {
        if (storyRepository.existsById(id)) {
            storyRepository.deleteById(id);
        } else throw new ResourceNotFoundException("No story found with id " + id);
    }

    public List<StoryOutputDto> getStoriesByAuthor(String username) {
        List<Story> stories = storyRepository.findByAuthorProfile_Username(username);
        if(stories.isEmpty()) {
            throw new ResourceNotFoundException("No stories found for username " + username);
        }
        return StoryMapper.storyModelListToOutputList(stories);
    }


}
