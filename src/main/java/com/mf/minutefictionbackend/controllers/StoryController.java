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
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PreAuthorize("hasAnyAuthority('AUTHOR', 'EDITOR')")
    @PostMapping("/submit/{themeId}")
    public ResponseEntity<StoryOutputDto> submitStory(@PathVariable Long themeId, @Valid @RequestBody StoryInputDto storyInputDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        StoryOutputDto storyDto = storyService.submitStory(storyInputDto, themeId, username);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + storyDto.getId()).toUriString());

        return ResponseEntity.created(uri).body(storyDto);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PutMapping("/editor/{storyId}/update")
    public ResponseEntity<StoryOutputDto> updateStory(@PathVariable("storyId") Long storyId, @Valid @RequestBody StoryInputDto updatedStory) {
        StoryOutputDto updatedStoryDto = storyService.updateStory(storyId, updatedStory);
        return ResponseEntity.ok().body(updatedStoryDto);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/editor/submitted")
    public ResponseEntity<List<StoryOutputDto>> getAllSubmittedStories() {
        List<StoryOutputDto> stories = storyService.getStoriesByStatus(StoryStatus.SUBMITTED);
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/editor/stories")
    public ResponseEntity<List<StoryOutputDto>> getStoriesByStatusAndThemeId(@RequestParam StoryStatus status, @RequestParam Long themeId) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeId(status, themeId);
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isAuthor(#storyId)")
    @GetMapping("/{storyId}")
    public ResponseEntity<StoryOutputDto> getStoryById(@PathVariable("storyId") Long storyId) {
        StoryOutputDto storyDto = storyService.getStoryById(storyId);
        return ResponseEntity.ok(storyDto);
    }

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isAuthor(#storyId)")
    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable("storyId") Long storyId) {
        storyService.deleteStoryById(storyId);
        return ResponseEntity.noContent().build();
    }



    // MANAGE ACCEPTING AND DECLINING STORIES

    @PreAuthorize("hasAuthority('EDITOR')")
    @PatchMapping("/editor/{storyId}/accept")
    public ResponseEntity<Void> acceptStory(@PathVariable("storyId") Long storyId) {
        storyService.acceptStory(storyId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PatchMapping("/editor/{storyId}/decline")
    public ResponseEntity<Void> declineStory(@PathVariable("storyId") Long storyId) {
        storyService.declineStory(storyId);
        return ResponseEntity.noContent().build();
    }
    

    // MANAGE PUBLISHING/PUBLISHED STORIES

    @PreAuthorize("hasAuthority('EDITOR')")
    @PatchMapping("/editor/{storyId}/publish")
    public ResponseEntity<Void> publishStory(@PathVariable Long storyId) {
        storyService.publishStory(storyId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PatchMapping("/editor/themes/{themeId}/publish")
    public ResponseEntity<Void> publishAllAcceptedStoriesByTheme(@PathVariable("themeId") Long themeId) {
        storyService.publishAllAcceptedStoriesByTheme(themeId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/published")
    public ResponseEntity<List<StoryOutputDto>> getAllPublishedStories() {
        List<StoryOutputDto> stories = storyService.getAllPublishedStoriesByDateDesc(StoryStatus.PUBLISHED);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/published/{storyId}")
    public ResponseEntity<StoryOutputDto> getPublishedStoryById(@PathVariable("storyId") Long storyId) {
        StoryOutputDto storyDto = storyService.getStoryByStatusAndStoryId(StoryStatus.PUBLISHED, storyId);
        return ResponseEntity.ok(storyDto);
    }



    // MANAGE STORIES BY THEME



    @GetMapping("/published/themes/{themeName}")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByThemeName(@PathVariable("themeName") String themeName) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeName(StoryStatus.PUBLISHED, themeName);
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/editor/{themeId}/stories")
    public ResponseEntity<List<StoryOutputDto>> getStoriesByThemeOfAllStatus(@PathVariable("themeId") Long themeId) {
        List<StoryOutputDto> relatedStories = storyService.getStoriesByTheme(themeId);
        return ResponseEntity.ok(relatedStories);
    }


    // COMMENTS ON STORY

    @GetMapping("/{storyId}/comments")
    public ResponseEntity<List<CommentOutputDto>> getCommentsByStory(@PathVariable Long storyId) {
        List<CommentOutputDto> comments = commentService.getCommentsByStory(storyId);
        return ResponseEntity.ok(comments);
    }


}
