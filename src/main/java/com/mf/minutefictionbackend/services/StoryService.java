package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.mappers.StoryMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.exceptions.BadRequestException;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.Theme;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import com.mf.minutefictionbackend.repositories.CommentRepository;
import com.mf.minutefictionbackend.repositories.StoryRepository;
import com.mf.minutefictionbackend.repositories.ThemeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final AuthorProfileRepository authorProfileRepository;
    private final ThemeRepository themeRepository;
    private final CommentRepository commentRepository;


    public StoryService(StoryRepository storyRepository, AuthorProfileRepository authorProfileRepository, ThemeRepository themeRepository, CommentRepository commentRepository) {
        this.storyRepository = storyRepository;
        this.authorProfileRepository = authorProfileRepository;
        this.themeRepository = themeRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public StoryOutputDto submitStory(StoryInputDto storyInputDto, Long themeId, String username) {
        AuthorProfile authorProfile = authorProfileRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("Author profile is required for submitting stories."));
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found."));

        if (theme.getClosingDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Theme closing date has already passed.");
        }

        int numberOfSubmissions = storyRepository.countSubmissionsByTheme(theme);
        if(numberOfSubmissions >= 50) {
            throw new BadRequestException("The maximum number of submissions for this theme has already been reached.");
        }

        boolean hasSubmitted = storyRepository.existsByThemeAndAuthorUsername(theme, username);
        if (hasSubmitted) {
            throw new BadRequestException("You have already submitted a story to this theme.");
        }

        Story story = StoryMapper.storyFromInputDtoToModel(storyInputDto);
        story.setStatus(StoryStatus.SUBMITTED);
        story.setAuthor(authorProfile);
        story.setTheme(theme);
        story.setPublishDate(null);

        Story savedStory = storyRepository.save(story);
        return StoryMapper.storyFromModelToOutputDto(savedStory);
    }

    @Transactional
    public StoryOutputDto updateStory(Long storyId, StoryInputDto updatedStory) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("No story found."));

        if(!story.getStatus().equals(StoryStatus.SUBMITTED)) {
            throw new BadRequestException("This story is already accepted, declined or published, therefore changes are not allowed.");
        }
        if(story.getTheme().getClosingDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Theme closing date has already passed, changes are no longer allowed.");
        }
        story.setContent(updatedStory.getContent());

        Story savedStory = storyRepository.save(story);
        return StoryMapper.storyFromModelToOutputDto(savedStory);
    }

    @Transactional
    public void publishStory(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found."));

        if (story.getStatus() == StoryStatus.ACCEPTED) {
            story.setStatus(StoryStatus.PUBLISHED);
            story.setPublishDate(LocalDate.now());
            storyRepository.save(story);
        } else {
            throw new IllegalArgumentException("Story must be accepted before being published.");
        }
    }

    @Transactional
    public void acceptStory(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found."));
        story.setStatus(StoryStatus.ACCEPTED);
        storyRepository.save(story);
    }

    @Transactional
    public void publishAllStoriesByStatusAndTheme(Long themeId) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("No theme found."));
        List<Story> storiesToPublish = storyRepository.findByStatusAndTheme(StoryStatus.ACCEPTED, theme);
        storiesToPublish.forEach(story -> {
            story.setStatus(StoryStatus.PUBLISHED);
            story.setPublishDate(LocalDate.now());
        });
        storyRepository.saveAll(storiesToPublish);
    }

    @Transactional
    public void declineStory(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found."));
        story.setStatus(StoryStatus.DECLINED);
        storyRepository.save(story);
    }

    @Transactional
    public void deleteStoryById(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("No story found with id " + storyId));
        List<Comment> comments = story.getComments();
        if(comments != null && !comments.isEmpty()) {
            commentRepository.deleteAll(comments);
        }
        storyRepository.delete(story);
    }

    public List<StoryOutputDto> getStoriesByStatus(StoryStatus status) {
        List<Story> stories = storyRepository.findByStatusOrderByPublishDateDesc(status);
        if (stories.isEmpty()) {
            throw new ResourceNotFoundException("No stories found with status " + status);
        }
        return StoryMapper.storyModelListToOutputList(stories);
    }

    public StoryOutputDto getSubmittedStoryById(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("No story found with id " + storyId));
        return StoryMapper.storyFromModelToOutputDto(story);
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


    public List<StoryOutputDto> getAllStoriesByAuthor(String username) {
        List<Story> allStories = storyRepository.findByAuthor_Username(username);
        return StoryMapper.storyModelListToOutputList(allStories);
    }


    public List<StoryOutputDto> getPublishedStoriesByAuthor(String username) {
        List<Story> publishedStories = storyRepository.findByAuthor_UsernameAndStatus(username, StoryStatus.PUBLISHED);
        return StoryMapper.storyModelListToOutputList(publishedStories);
    }



    public StoryOutputDto getStoryByStatusAndStoryId(StoryStatus storyStatus, Long storyId) {
        Story story = storyRepository.findByStatusAndId(storyStatus, storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        return StoryMapper.storyFromModelToOutputDto(story);
    }



}
