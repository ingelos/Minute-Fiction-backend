package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.mappers.StoryMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.exceptions.MethodArgumentNotValidException;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.Theme;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import com.mf.minutefictionbackend.repositories.StoryRepository;
import com.mf.minutefictionbackend.repositories.ThemeRepository;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


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

        if (theme.getClosingDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Theme closing date has passed");
        }

        Story story = StoryMapper.storyFromInputDtoToModel(storyInputDto, authorProfile, theme);
        story.setStatus(StoryStatus.SUBMITTED);

        Story savedStory = storyRepository.save(story);
        return StoryMapper.storyFromModelToOutputDto(savedStory);
    }

    public StoryOutputDto publishStory(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));

        story.setStatus(StoryStatus.PUBLISHED);
        story.setPublishDate(LocalDate.now());
        Story publishedStory = storyRepository.save(story);
        return StoryMapper.storyFromModelToOutputDto(publishedStory);
    }


    public List<StoryOutputDto> getStoriesByStatus(StoryStatus status) {
        List<Story> stories = storyRepository.findByStatus(status);
        if (stories.isEmpty()) {
            throw new ResourceNotFoundException("No stories found with status " + status);
        }
        return StoryMapper.storyModelListToOutputList(stories);
    }


    public StoryOutputDto getStoryById(Long id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No story found with id " + id));
        return StoryMapper.storyFromModelToOutputDto(story);
    }

    public void deleteStoryById(Long id) {
        if (storyRepository.existsById(id)) {
            throw new ResourceNotFoundException("No story found with id " + id);
        }
        storyRepository.deleteById(id);
    }


    public List<StoryOutputDto> getStoriesByStatusAndThemeId(StoryStatus status, Long themeId) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("No theme found with id " + themeId));

        List<Story> stories = storyRepository.findByStatusAndTheme(status, theme);
        return StoryMapper.storyModelListToOutputList(stories);
    }


    public List<StoryOutputDto> getStoriesByStatusAndThemeName(StoryStatus status, String themeName) {
        Theme theme = themeRepository.findByNameIgnoreCase(themeName)
                .orElseThrow(() -> new IllegalArgumentException("No theme found with name " + themeName));

        List<Story> stories = storyRepository.findByStatusAndTheme(status, theme);
        return StoryMapper.storyModelListToOutputList(stories);
    }


    public List<StoryOutputDto> getPublishedStoriesByAuthor(String username) {

        List<Story> stories = storyRepository.findByAuthorProfile_UsernameAndStatus(username, StoryStatus.PUBLISHED);
        return StoryMapper.storyModelListToOutputList(stories);
    }


}
