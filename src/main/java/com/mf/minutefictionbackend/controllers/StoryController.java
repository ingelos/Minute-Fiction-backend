package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.services.CommentService;
import com.mf.minutefictionbackend.services.SecurityService;
import com.mf.minutefictionbackend.services.StoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;
    private final CommentService commentService;
    private final SecurityService securityService;

    public StoryController(StoryService storyService, CommentService commentService, SecurityService securityService) {
        this.storyService = storyService;
        this.commentService = commentService;
        this.securityService = securityService;
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/submit/{themeId}")
    public ResponseEntity<StoryOutputDto> submitStory(@Valid @PathVariable Long themeId, @RequestParam String username, @RequestBody StoryInputDto storyInputDto) {
        StoryOutputDto storyDto = storyService.submitStory(storyInputDto, themeId, username);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + storyDto.getId()).toUriString());

        return ResponseEntity.created(uri).body(storyDto);
    }

    @PreAuthorize("@securityService.isStoryOwner(storyId)")
    @PatchMapping("/submit/{storyId}")
    public ResponseEntity<StoryOutputDto> updateSubmittedStory(@Valid @PathVariable("storyId") Long storyId, @RequestBody StoryInputDto updatedStory) {

        StoryOutputDto updatedStoryDto = storyService.updateStory(storyId, updatedStory);
        return ResponseEntity.ok().body(updatedStoryDto);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/submitted")
    public ResponseEntity<List<StoryOutputDto>> getAllSubmittedStories() {

        List<StoryOutputDto> stories = storyService.getStoriesByStatus(StoryStatus.SUBMITTED);
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/submitted/{themeId}")
    public ResponseEntity<List<StoryOutputDto>> getSubmittedStoriesByThemeId(@PathVariable("themeId") Long themeId) {

        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeId(StoryStatus.SUBMITTED, themeId);
        return ResponseEntity.ok(stories);
    }


    // publishing/published

    @PatchMapping("/publish/{storyId}")
    public ResponseEntity<StoryOutputDto> publishStory(@PathVariable Long storyId) {
        if(!securityService.isEditor()) {
            throw new AccessDeniedException("You do not have permission to create mailings.");
        }
        StoryOutputDto story = storyService.publishStory(storyId);
        return ResponseEntity.ok(story);
    }

    @GetMapping("/published")
    public ResponseEntity<List<StoryOutputDto>> getAllPublishedStories() {
        List<StoryOutputDto> stories = storyService.getStoriesByStatus(StoryStatus.PUBLISHED);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/published/{storyId}")
    public ResponseEntity<StoryOutputDto> getPublishedStoryById(@PathVariable("storyId") Long storyId) {
        StoryOutputDto storyDto = storyService.getStoryByStatusAndStoryId(StoryStatus.PUBLISHED, storyId);
        return ResponseEntity.ok(storyDto);
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<StoryOutputDto> getStoryById(@PathVariable("storyId") Long storyId) {
        StoryOutputDto storyDto = storyService.getStoryById(storyId);
        return ResponseEntity.ok().body(storyDto);
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable("storyId") Long storyId) {
        if(!securityService.isStoryOwnerOrEditor(storyId)) {
            throw new AccessDeniedException("You do not have permission to create mailings.");
        }
        storyService.deleteStoryById(storyId);
        return ResponseEntity.noContent().build();
    }



    // get published stories by theme id

    @GetMapping("/published/theme/{themeId}")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByThemeId(@PathVariable("themeId") Long themeId) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeId(StoryStatus.PUBLISHED, themeId);
        return ResponseEntity.ok(stories);
    }


    // get published stories by theme name

    @GetMapping("/published/themename/{themeName}")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByThemeName(@PathVariable("themeName") String themeName) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeName(StoryStatus.PUBLISHED, themeName);
        return ResponseEntity.ok(stories);
    }


    // story related comments

    @GetMapping("/{storyId}/comments")
    public ResponseEntity<List<CommentOutputDto>> getCommentsByStory(@PathVariable Long storyId) {
        List<CommentOutputDto> comments = commentService.getCommentsByStory(storyId);
        return ResponseEntity.ok(comments);
    }


}
