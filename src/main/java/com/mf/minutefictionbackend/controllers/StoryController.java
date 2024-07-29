package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.services.CommentService;
import com.mf.minutefictionbackend.services.StoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
//@RequestMapping("/stories")
public class StoryController {

    private final StoryService storyService;
    private final CommentService commentService;

    public StoryController(StoryService storyService, CommentService commentService) {
        this.storyService = storyService;
        this.commentService = commentService;
    }



    @PostMapping("/stories/submit")
    public ResponseEntity<StoryOutputDto> submitStory(@Valid @RequestBody StoryInputDto storyInputDto, @RequestParam String profileId, @RequestParam Long themeId) {
        StoryOutputDto storyDto = storyService.submitStory(storyInputDto, profileId, themeId);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + storyDto.id).toUriString());
        return ResponseEntity.created(uri).body(storyDto);
    }


    @PutMapping("stories/publish/{storyId}")
    public ResponseEntity<StoryOutputDto> publishStory(@PathVariable Long storyId) {
        StoryOutputDto story = storyService.publishStory(storyId);
        return ResponseEntity.ok(story);
    }

    // divide in getSubmittedStories and getPublishedStories

    @GetMapping("stories/submitted")
    public ResponseEntity<List<StoryOutputDto>> getAllSubmittedStories() {
        List<StoryOutputDto> stories = storyService.getStoriesByStatus(StoryStatus.SUBMITTED);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("stories/published")
    public ResponseEntity<List<StoryOutputDto>> getAllPublishedStories() {
        List<StoryOutputDto> stories = storyService.getStoriesByStatus(StoryStatus.PUBLISHED);
        return ResponseEntity.ok(stories);
    }



    @GetMapping("stories/{id}")
    public ResponseEntity<StoryOutputDto> getStoryById(@PathVariable ("id") Long id) {
        StoryOutputDto optionalStory = storyService.getStoryById(id);
        return ResponseEntity.ok().body(optionalStory);
    }

    @DeleteMapping("stories/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable("id") Long id) {
        storyService.deleteStoryById(id);
        return ResponseEntity.noContent().build();
    }

    // get submitted stories by theme

    @GetMapping("/stories/submitted/{themeId}")
    public ResponseEntity<List<StoryOutputDto>> getSubmittedStoriesByTheme(@PathVariable("themeId") Long themeId) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndTheme(StoryStatus.SUBMITTED, themeId);
        return ResponseEntity.ok(stories);
    }

    // get published stories by theme

    @GetMapping("/stories/published/{themeId}")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByTheme(@PathVariable("themeId") Long themeId) {
        List<StoryOutputDto> stories = storyService.getStoriesByStatusAndTheme(StoryStatus.PUBLISHED, themeId);
        return ResponseEntity.ok(stories);
    }




    // get comments on story

    @GetMapping("/stories/{storyId}/comments")
    public ResponseEntity<List<CommentOutputDto>> getCommentsByStory(@PathVariable Long storyId) {
        List<CommentOutputDto> comments = commentService.getCommentsByStory(storyId);
        return ResponseEntity.ok(comments);
    }



}
