package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
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



    @PostMapping
    public ResponseEntity<StoryOutputDto> createStory(@Valid @RequestBody StoryInputDto storyInputDto) {
        StoryOutputDto story = storyService.createStory(storyInputDto);
        // add authority / username in security config

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + story.title).toUriString());
        // add story author name to path

        return ResponseEntity.created(uri).body(story);
    }

    @GetMapping
    public ResponseEntity<List<StoryOutputDto>> getAllStories() {
        return ResponseEntity.ok().body(storyService.getAllStories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryOutputDto> getStoryById(@PathVariable ("id") Long id) {
        StoryOutputDto optionalStory = storyService.getStoryById(id);
        return ResponseEntity.ok().body(optionalStory);
    }

    @GetMapping("/{id}/comments")
    public List<CommentOutputDto> getAllCommentsByStory(@PathVariable Long id) {
        return commentService.getAllCommentsOnStory(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable("id") Long id) {
        storyService.deleteStoryById(id);
        return ResponseEntity.noContent().build();
    }
    
    

}
