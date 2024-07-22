package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.mappers.StoryMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
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

        if(theme.getClosingDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Theme closing date has passed");
        }

        Story story = StoryMapper.storyFromInputDtoToModel(storyInputDto, authorProfile, theme);
        story.setStatus(StoryStatus.SUBMITTED);
        story = storyRepository.save(story);
        return StoryMapper.storyFromModelToOutputDto(story);
    }

    public StoryOutputDto publishStory(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));

        story.setStatus(StoryStatus.PUBLISHED);
        story.setPublishDate(LocalDate.now());
        story = storyRepository.save(story);
        return StoryMapper.storyFromModelToOutputDto(story);
    }



    public List<StoryOutputDto> getStoriesByStatus(StoryStatus status) {
        List<Story> stories = storyRepository.findByStatus(status);
        if(stories.isEmpty()) {
            throw new ResourceNotFoundException("No stories found with status " + status);
        }
        return StoryMapper.storyModelListToOutputList(stories);
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


    public List<StoryOutputDto> getStoriesByStatusAndTheme(StoryStatus status, Long themeId) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("No theme found with id " + themeId));
        List<Story> stories = storyRepository.findByStatusAndTheme(status, theme);
        return StoryMapper.storyModelListToOutputList(stories);
    }


}
