package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.mappers.StoryMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.Theme;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import com.mf.minutefictionbackend.repositories.StoryRepository;
import com.mf.minutefictionbackend.repositories.ThemeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final AuthorProfileRepository authorProfileRepository;
    private final ThemeRepository themeRepository;

    public StoryService(StoryRepository storyRepository, AuthorProfileRepository authorProfileRepository, ThemeRepository themeRepository) {
        this.storyRepository = storyRepository;
        this.authorProfileRepository = authorProfileRepository;
        this.themeRepository = themeRepository;
    }


    public StoryOutputDto submitStory(StoryInputDto storyInputDto, String username, Long themeId) {
        AuthorProfile authorProfile = authorProfileRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Authorprofile not found"));
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found"));

        Story story = storyRepository.save(StoryMapper.storyFromInputDtoToModel(storyInputDto, authorProfile, theme));
        return StoryMapper.storyFromModelToOutputDto(story);
    }

    public StoryOutputDto publishStory(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        story.setStatus("published");
        story.setPublishDate(LocalDate.now());
        story = storyRepository.save(story);
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
        if(!stories.isEmpty()) {
            return StoryMapper.storyModelListToOutputList(stories);
        } else throw new ResourceNotFoundException("No stories found for username " + username);
    }



}
