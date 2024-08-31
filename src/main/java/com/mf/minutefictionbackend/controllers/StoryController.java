package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.services.CommentService;
import com.mf.minutefictionbackend.services.StoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;
    private final CommentService commentService;

    public StoryController(StoryService storyService, CommentService commentService) {
        this.storyService = storyService;
        this.commentService = commentService;
    }


    @PostMapping("/submit/{themeId}")
    public ResponseEntity<StoryOutputDto> submitStory(@Valid @PathVariable Long themeId, @RequestParam String username, @RequestBody StoryInputDto storyInputDto) {
        StoryOutputDto storyDto = storyService.submitStory(storyInputDto, themeId, username);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + storyDto.getId()).toUriString());

        return ResponseEntity.created(uri).body(storyDto);
    }

    @PatchMapping("/{storyId}")
    public ResponseEntity<StoryOutputDto> updateSubmittedStory(@Valid @PathVariable("storyId") Long storyId, @RequestBody StoryInputDto updatedStory) {
        StoryOutputDto updatedStoryDto = storyService.updateStory(storyId, updatedStory);
        return ResponseEntity.ok().body(updatedStoryDto);
    }

    @GetMapping("/submitted")
    public ResponseEntity<List<StoryOutputDto>> getAllSubmittedStories() {
        List<StoryOutputDto> stories = storyService.getStoriesByStatus(StoryStatus.SUBMITTED);
        return ResponseEntity.ok(stories);
    }


    @PatchMapping("/publish/{storyId}")
    public ResponseEntity<StoryOutputDto> publishStory(@PathVariable Long storyId) {
        StoryOutputDto story = storyService.publishStory(storyId);

        //authority editor

        return ResponseEntity.ok(story);
    }

    @GetMapping("/published")
    public ResponseEntity<List<StoryOutputDto>> getAllPublishedStories() {
        List<StoryOutputDto> stories = storyService.getStoriesByStatus(StoryStatus.PUBLISHED);
        return ResponseEntity.ok(stories);
    }


    @GetMapping("/{storyId}")
    public ResponseEntity<StoryOutputDto> getStoryById(@PathVariable ("storyId") Long storyId) {
        StoryOutputDto storyDto = storyService.getStoryById(storyId);
        return ResponseEntity.ok().body(storyDto);
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable("storyId") Long storyId) {
        storyService.deleteStoryById(storyId);
        return ResponseEntity.noContent().build();
    }

    // get submitted stories by theme id

    @GetMapping("/submitted/{themeId}")
    public ResponseEntity<List<StoryOutputDto>> getSubmittedStoriesByThemeId(@PathVariable("themeId") Long themeId) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeId(StoryStatus.SUBMITTED, themeId);
        return ResponseEntity.ok(stories);
    }

    // get published stories by theme id

    @GetMapping("/published/{themeId}")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByThemeId(@PathVariable("themeId") Long themeId) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeId(StoryStatus.PUBLISHED, themeId);
        return ResponseEntity.ok(stories);
    }


    // get published stories by theme name

    @GetMapping("/published/themes/{themeName}")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByThemeName(@PathVariable("themeName") String themeName) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeName(StoryStatus.PUBLISHED, themeName);
        return ResponseEntity.ok(stories);
    }


    // get comments on story

    @GetMapping("/{storyId}/comments")
    public ResponseEntity<List<CommentOutputDto>> getCommentsByStory(@PathVariable Long storyId) {
        List<CommentOutputDto> comments = commentService.getCommentsByStory(storyId);
        return ResponseEntity.ok(comments);
    }





}
