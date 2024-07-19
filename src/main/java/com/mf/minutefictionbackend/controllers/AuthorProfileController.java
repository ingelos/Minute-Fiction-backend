package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.services.AuthorProfileService;
import com.mf.minutefictionbackend.services.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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


    @PostMapping
    public ResponseEntity<AuthorProfileOutputDto> createAuthorProfile(AuthorProfileInputDto authorProfileInputDto) {
        AuthorProfileOutputDto authorProfile = authorProfileService.createAuthorProfile(authorProfileInputDto);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + authorProfile.username).toUriString());

        return ResponseEntity.created(uri).body(authorProfile);
    }

    @GetMapping("/{username}/stories")
    public List<StoryOutputDto> getStoriesByUsername(@PathVariable String username) {
        return storyService.getStoriesByAuthorUsername(username);
    }

    @GetMapping
    public ResponseEntity<List<AuthorProfileOutputDto>> getAllAuthorProfiles() {
        return ResponseEntity.ok().body(authorProfileService.getAllAuthorProfiles());
    }

    @GetMapping("/{username}")
    public ResponseEntity<AuthorProfileOutputDto> getAuthorProfileByUsername(@PathVariable("username") String username) {
        AuthorProfileOutputDto optionalAuthorProfile = authorProfileService.getAuthorProfileByUsername(username);
        return ResponseEntity.ok().body(optionalAuthorProfile);
    }

    @PutMapping("/{username}")
    public ResponseEntity<AuthorProfileOutputDto> updateAuthorProfile(@PathVariable("username") String username, @RequestBody AuthorProfileOutputDto updatedProfile) {
        AuthorProfileOutputDto authorProfileDto = authorProfileService.updateAuthorProfile(username, updatedProfile);
        return ResponseEntity.ok().body(authorProfileDto);
    }

}
