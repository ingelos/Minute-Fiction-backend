package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.StoryInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.services.StoryService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoryController {

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    private final StoryService storyService;

    @PostMapping
    public ResponseEntity<StoryOutputDto> createStory(@RequestBody StoryInputDto storyInputDto) {
        StoryOutputDto story = storyService.createStory(storyInputDto);
        // add authority / username

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

    @DeleteMapping("/{id}")
    public ResponseEntity<StoryOutputDto> deleteStory(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(storyService.deleteStory(id));
    }
    
    

}
