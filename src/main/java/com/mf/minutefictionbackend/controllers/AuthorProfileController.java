package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.services.AuthorProfileService;
import com.mf.minutefictionbackend.services.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/authorprofiles")
public class AuthorProfileController {

    private final AuthorProfileService authorProfileService;
    private final StoryService storyService;


    public AuthorProfileController(AuthorProfileService authorProfileService, StoryService storyService) {
        this.authorProfileService = authorProfileService;
        this.storyService = storyService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorProfileOutputDto>> getAllAuthorProfiles() {
        List<AuthorProfileOutputDto> authorProfiles = authorProfileService.getAllAuthorProfiles();
        return ResponseEntity.ok(authorProfiles);
    }


    @PutMapping("/{username}")
    public ResponseEntity<AuthorProfileOutputDto> updateAuthorProfile(@PathVariable("username") String username, @RequestBody AuthorProfileOutputDto updatedProfile) {
        AuthorProfileOutputDto authorProfileDto = authorProfileService.updateAuthorProfile(username, updatedProfile);
        return ResponseEntity.ok().body(authorProfileDto);
    }


    @GetMapping("/{username}")
    public ResponseEntity<AuthorProfileOutputDto> getAuthorProfileByUsername(@PathVariable("username") String username) {
        AuthorProfileOutputDto authorProfile = authorProfileService.getAuthorProfileByUsername(username);
        return ResponseEntity.ok().body(authorProfile);
    }


    // get published stories by author
    @GetMapping("/{username}/stories")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByAuthor(@PathVariable String username) {
        List<StoryOutputDto> stories = storyService.getPublishedStoriesByAuthor(username);
        return ResponseEntity.ok(stories);
    }



}
