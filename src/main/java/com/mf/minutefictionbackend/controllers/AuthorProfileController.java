package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.services.AuthorProfileService;
import com.mf.minutefictionbackend.services.PhotoService;
import com.mf.minutefictionbackend.services.SecurityService;
import com.mf.minutefictionbackend.services.StoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/authorprofiles")
public class AuthorProfileController {

    private final AuthorProfileService authorProfileService;
    private final StoryService storyService;
    private final PhotoService photoService;

    private final SecurityService securityService;


    public AuthorProfileController(AuthorProfileService authorProfileService, StoryService storyService, PhotoService photoService, SecurityService securityService) {
        this.authorProfileService = authorProfileService;
        this.storyService = storyService;
        this.photoService = photoService;
        this.securityService = securityService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<AuthorProfileOutputDto> createAuthorProfile(@Valid @PathVariable String username, @RequestBody AuthorProfileInputDto authorProfileInputDto) {
        if(!securityService.isOwner(username)) {
            throw new AccessDeniedException("You do not have permission to perform this action.");
        }
        AuthorProfileOutputDto createdProfile = authorProfileService.createAuthorProfile(username, authorProfileInputDto);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + createdProfile.getUsername())
                .toUriString());

        return ResponseEntity.created(uri).body(createdProfile);
    }

    @GetMapping
    public ResponseEntity<List<AuthorProfileOutputDto>> getAllAuthorProfiles() {
        List<AuthorProfileOutputDto> authorProfiles = authorProfileService.getAllAuthorProfiles();
        return ResponseEntity.ok(authorProfiles);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<AuthorProfileOutputDto> updateAuthorProfile(@Valid @PathVariable("username") String username, @RequestBody AuthorProfileInputDto updatedProfile) {
        if(!securityService.isOwnerOrEditor(username)) {
            throw new AccessDeniedException("You do not have permission to edit this profile");
        }
        AuthorProfileOutputDto authorProfileDto = authorProfileService.updateAuthorProfile(username, updatedProfile);
        return ResponseEntity.ok().body(authorProfileDto);
    }


    @GetMapping("/{username}")
    public ResponseEntity<AuthorProfileOutputDto> getAuthorProfileByUsername(@PathVariable("username") String username) {
        AuthorProfileOutputDto authorProfile = authorProfileService.getAuthorProfileByUsername(username);
        return ResponseEntity.ok().body(authorProfile);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteAuthorProfile(@PathVariable("username") String username) {
        if(!securityService.isOwnerOrEditor(username)) {
            throw new AccessDeniedException("You do not have permission to perform this action.");
        }
        authorProfileService.deleteAuthorProfile(username);
        return ResponseEntity.noContent().build();
    }


    // get published stories by author

    @GetMapping("/{username}/stories")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByAuthor(@PathVariable String username) {
        List<StoryOutputDto> stories = storyService.getPublishedStoriesByAuthor(username);
        return ResponseEntity.ok(stories);
    }

    // download function for downloading own published stories??





    // MANAGING PHOTOS

    @PostMapping("/{username}/photo")
    public ResponseEntity<AuthorProfileOutputDto> addPhotoToAuthorProfile(@Valid @PathVariable("username") String username, @RequestBody MultipartFile file)
        throws IOException {

        if(!securityService.isOwner(username)) {
            throw new AccessDeniedException("You do not have permission to add a photo to this profile.");
        }
            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/authorprofiles/")
                    .path(Objects.requireNonNull(username))
                    .path("/photo")
                    .toUriString();

        String fileName = photoService.storeFile(file);
        AuthorProfileOutputDto authorProfile = authorProfileService.assignPhotoToAuthorProfile(fileName, username);

        return ResponseEntity.created(URI.create(url)).body(authorProfile);
    }

    @GetMapping("/{username}/photo")
    public ResponseEntity<Resource> getAuthorProfilePhoto(@PathVariable("username") String username, HttpServletRequest request) {
        Resource resource = authorProfileService.getPhotoForAuthorProfile(username);
        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }

    @DeleteMapping("/{username}/photo")
    public ResponseEntity<Void> deleteProfilePhoto(@PathVariable("username") String username) {
        if(!securityService.isOwnerOrEditor(username)) {
            throw new AccessDeniedException("You do not have permission to delete this photo.");
        }
        authorProfileService.deletePhotoByUsername(username);
        return ResponseEntity.noContent().build();
    }




}
