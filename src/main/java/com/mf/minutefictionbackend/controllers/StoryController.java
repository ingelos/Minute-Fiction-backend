package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.services.CommentService;
import com.mf.minutefictionbackend.services.StoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    public StoryController(StoryService storyService, CommentService commentService) {
        this.storyService = storyService;
        this.commentService = commentService;
    }


    // MANAGE SUBMITTING/SUBMITTED STORIES

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/submit/{themeId}")
    public ResponseEntity<StoryOutputDto> submitStory(@Valid @PathVariable Long themeId, @RequestParam String username, @RequestBody StoryInputDto storyInputDto) {
        StoryOutputDto storyDto = storyService.submitStory(storyInputDto, themeId, username);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + storyDto.getId()).toUriString());
        return ResponseEntity.created(uri).body(storyDto);
    }

    @PreAuthorize("@securityService.isAuthor(storyId)")
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
    @GetMapping("/submitted/{storyId}")
    public ResponseEntity<StoryOutputDto> getSubmittedStoryById(@PathVariable("storyId") Long storyId) {
        StoryOutputDto storyDto = storyService.getSubmittedStoryById(storyId);
        return ResponseEntity.ok().body(storyDto);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/submitted/{themeId}")
    public ResponseEntity<List<StoryOutputDto>> getSubmittedStoriesByThemeId(@PathVariable("themeId") Long themeId) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeId(StoryStatus.SUBMITTED, themeId);
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isAuthor(storyId)")
    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable("storyId") Long storyId) {
        storyService.deleteStoryById(storyId);
        return ResponseEntity.noContent().build();
    }

    // MANAGE ACCEPTED STORIES


    @PreAuthorize("hasAuthority('EDITOR')")
    @PatchMapping("/submitted/{storyId}")
    public ResponseEntity<Void> acceptStory(@PathVariable("storyId") Long storyId) {
        storyService.acceptStory(storyId);
        return ResponseEntity.noContent().build();
    }


    // MANAGE DECLINED STORIES

    @PreAuthorize("hasAuthority('EDITOR')")
    @PatchMapping("/decline/{storyId}")
    public ResponseEntity<Void> declineStory(@PathVariable("storyId") Long storyId) {
        storyService.declineStory(storyId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/declined")
    public ResponseEntity<List<StoryOutputDto>> getAllDeclinedStories() {
        List<StoryOutputDto> declinedStories = storyService.getStoriesByStatus(StoryStatus.DECLINED);
        return ResponseEntity.ok(declinedStories);
    }


    // MANAGE PUBLISHING/PUBLISHED STORIES

    @PreAuthorize("hasAuthority('EDITOR')")
    @PatchMapping("/themes/{themeId}/publish")
    public ResponseEntity<Void> publishAllStoriesByStatusAndTheme(@PathVariable("themeId") Long themeId) {
        storyService.publishAllStoriesByStatusAndTheme(themeId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PatchMapping("/publish/{storyId}")
    public ResponseEntity<Void> publishStory(@PathVariable Long storyId) {
        storyService.publishStory(storyId);
        return ResponseEntity.noContent().build();
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



    // MANAGE STORIES BY THEME

    @GetMapping("/published/theme/{themeId}")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByThemeId(@PathVariable("themeId") Long themeId) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeId(StoryStatus.PUBLISHED, themeId);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/published/themes/{themeName}")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByThemeName(@PathVariable("themeName") String themeName) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeName(StoryStatus.PUBLISHED, themeName);
        return ResponseEntity.ok(stories);
    }


    // COMMENTS ON STORY

    @GetMapping("/{storyId}/comments")
    public ResponseEntity<List<CommentOutputDto>> getCommentsByStory(@PathVariable Long storyId) {
        List<CommentOutputDto> comments = commentService.getCommentsByStory(storyId);
        return ResponseEntity.ok(comments);
    }


}
