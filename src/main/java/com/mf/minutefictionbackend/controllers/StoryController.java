package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.services.CommentService;
import com.mf.minutefictionbackend.services.StoryService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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



    @PostMapping("/submit")
    public ResponseEntity<StoryOutputDto> submitStory(@Valid @RequestBody StoryInputDto storyInputDto, @RequestParam String username, @RequestParam Long themeId) {
        StoryOutputDto storyDto = storyService.submitStory(storyInputDto, username, themeId);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + storyDto.id).toUriString());
        return ResponseEntity.created(uri).body(storyDto);
    }


    @PutMapping("/publish/{storyId}")
    public ResponseEntity<StoryOutputDto> publishStory(@PathVariable Long storyId) {
        StoryOutputDto storyOutputDto = storyService.publishStory(storyId);
        return ResponseEntity.ok(storyOutputDto);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStory(@PathVariable("id") Long id) {
        storyService.deleteStoryById(id);
        return ResponseEntity.noContent().build();
    }

    // get comments by story

    @GetMapping("/{storyId}/comments")
    public ResponseEntity<List<CommentOutputDto>> getCommentsByStory(@PathVariable Long storyId) {
        List<CommentOutputDto> comments = commentService.getCommentsByStory(storyId);
        return ResponseEntity.ok(comments);
    }

}
