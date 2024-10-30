package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.mappers.StoryMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.services.AuthorProfileService;
import com.mf.minutefictionbackend.services.PdfGeneratorService;
import com.mf.minutefictionbackend.services.PhotoService;
import com.mf.minutefictionbackend.services.StoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final PdfGeneratorService pdfGeneratorService;


    public AuthorProfileController(AuthorProfileService authorProfileService, StoryService storyService, PhotoService photoService, PdfGeneratorService pdfGeneratorService) {
        this.authorProfileService = authorProfileService;
        this.storyService = storyService;
        this.photoService = photoService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isOwner(#username)")
    @PostMapping("/{username}")
    public ResponseEntity<AuthorProfileOutputDto> createAuthorProfile(@Valid @PathVariable String username, @RequestBody AuthorProfileInputDto authorProfileInputDto) {
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

    @PreAuthorize("@securityService.isOwner(#username)")
    @PutMapping("/{username}")
    public ResponseEntity<AuthorProfileOutputDto> updateAuthorProfile(@Valid @PathVariable("username") String username, @RequestBody AuthorProfileInputDto updatedProfile) {
        AuthorProfileOutputDto authorProfileDto = authorProfileService.updateAuthorProfile(username, updatedProfile);
        return ResponseEntity.ok().body(authorProfileDto);
    }

    @GetMapping("/{username}")
    public ResponseEntity<AuthorProfileOutputDto> getAuthorProfileByUsername(@PathVariable("username") String username) {
        AuthorProfileOutputDto authorProfile = authorProfileService.getAuthorProfileByUsername(username);
        return ResponseEntity.ok().body(authorProfile);
    }

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isOwner(#username)")
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteAuthorProfile(@PathVariable("username") String username) {
        authorProfileService.deleteAuthorProfile(username);
        return ResponseEntity.noContent().build();
    }



    // MANAGE STORIES BY AUTHOR

    @GetMapping("/{username}/published")
    public ResponseEntity<List<StoryOutputDto>> getPublishedStoriesByAuthor(@PathVariable String username) {
        List<StoryOutputDto> stories = storyService.getPublishedStoriesByAuthor(username);
        return ResponseEntity.ok(stories);
    }

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isOwner(#username)")
    @GetMapping("/{username}/overview")
    public ResponseEntity<List<StoryOutputDto>> getAllStoriesByAuthor(@PathVariable String username) {
        List<StoryOutputDto> stories = storyService.getAllStoriesByAuthor(username);
        return ResponseEntity.ok(stories);
    }




    // MANAGE DOWNLOADING OF STORIES

    @PreAuthorize("@securityService.isOwner(#username)")
    @GetMapping("/{username}/stories/download")
    public void downloadStoriesAsPDF(@PathVariable("username") String username, HttpServletResponse response) throws Exception {
        List<StoryOutputDto> storyDtos = storyService.getPublishedStoriesByAuthor(username);
        List<Story> stories = StoryMapper.storyOutputListToModelList(storyDtos);
        pdfGeneratorService.exportStoriesToPdf(stories, response);
    }



    // MANAGING PHOTOS

    @PreAuthorize("@securityService.isOwner(#username)")
    @PostMapping("/{username}/photo")
    public ResponseEntity<AuthorProfileOutputDto> addPhotoToAuthorProfile(@Valid @PathVariable("username") String username, @RequestBody MultipartFile file)
        throws IOException {
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

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isOwner(#username)")
    @DeleteMapping("/{username}/photo")
    public ResponseEntity<Void> deleteProfilePhoto(@PathVariable("username") String username) {
        authorProfileService.deletePhotoByUsername(username);
        return ResponseEntity.noContent().build();
    }


}
