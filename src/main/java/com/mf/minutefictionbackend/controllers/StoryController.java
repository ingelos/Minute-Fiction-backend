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
    private final SecurityService securityService;

    public StoryController(StoryService storyService, CommentService commentService, SecurityService securityService) {
        this.storyService = storyService;
        this.commentService = commentService;
        this.securityService = securityService;
    }


    // MANAGE SUBMITTING/SUBMITTED STORIES

    @PostMapping("/submit/{themeId}")
    public ResponseEntity<StoryOutputDto> submitStory(@PathVariable Long themeId, @Valid @RequestBody StoryInputDto storyInputDto) {
        securityService.checkIsAuthor();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        StoryOutputDto storyDto = storyService.submitStory(storyInputDto, themeId, username);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + storyDto.getId()).toUriString());

        return ResponseEntity.created(uri).body(storyDto);
    }

    @PatchMapping("/editor/{storyId}/update")
    public ResponseEntity<StoryOutputDto> updateStory(@PathVariable("storyId") Long storyId, @Valid @RequestBody StoryInputDto updatedStory) {
        securityService.checkIsEditor();
        StoryOutputDto updatedStoryDto = storyService.updateStory(storyId, updatedStory);
        return ResponseEntity.ok().body(updatedStoryDto);
    }


    @GetMapping("/editor/overview")
    public ResponseEntity<List<StoryOutputDto>> getStoriesByStatusAndThemeId(@RequestParam(required = false) StoryStatus status, @RequestParam(required = false) Long themeId) {
        securityService.checkIsEditor();
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndThemeId(status, themeId);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<StoryOutputDto> getStoryById(@PathVariable("storyId") Long storyId) {
        securityService.checkIsEditorOrAuthor(storyId);

        StoryOutputDto storyDto = storyService.getStoryById(storyId);
        return ResponseEntity.ok(storyDto);
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable("storyId") Long storyId) {
        securityService.checkIsEditorOrAuthor(storyId);

        storyService.deleteStoryById(storyId);
        return ResponseEntity.noContent().build();
    }


    // MANAGE ACCEPTING AND DECLINING STORIES

    @PatchMapping("/editor/{storyId}/accept")
    public ResponseEntity<Void> acceptStory(@PathVariable("storyId") Long storyId) {
        securityService.checkIsEditor();

        storyService.acceptStory(storyId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/editor/{storyId}/decline")
    public ResponseEntity<Void> declineStory(@PathVariable("storyId") Long storyId) {
        securityService.checkIsEditor();

        storyService.declineStory(storyId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/editor/{storyId}/status")
    public ResponseEntity<Void> updateStoryStatus(@PathVariable("storyId") Long storyId, @RequestParam StoryStatus status) {
        securityService.checkIsEditor();

        storyService.updateStoryStatus(storyId, status);
        return ResponseEntity.noContent().build();
    }


    // MANAGE PUBLISHING/PUBLISHED STORIES

    @PatchMapping("/editor/{storyId}/publish")
    public ResponseEntity<Void> publishStory(@PathVariable Long storyId) {
        securityService.checkIsEditor();

        storyService.publishStory(storyId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/editor/themes/{themeId}/publish")
    public ResponseEntity<Void> publishAllAcceptedStoriesByTheme(@PathVariable("themeId") Long themeId) {
        securityService.checkIsEditor();

        storyService.publishAllAcceptedStoriesByTheme(themeId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/published")
    public ResponseEntity<List<StoryOutputDto>> getAllPublishedStories(@RequestParam(required = false, defaultValue = "10") int limit, @RequestParam(required = false, defaultValue ="0") int offset) {
        List<StoryOutputDto> stories = storyService.getAllPublishedStoriesByDateDescWithPagination(StoryStatus.PUBLISHED, limit, offset);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/published/{storyId}")
    public ResponseEntity<StoryOutputDto> getPublishedStoryById(@PathVariable("storyId") Long storyId) {
        StoryOutputDto storyDto = storyService.getStoryByStatusAndStoryId(StoryStatus.PUBLISHED, storyId);
        return ResponseEntity.ok(storyDto);
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
