package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.CommentInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/stories/{storyId}/comments")
    public ResponseEntity<CommentOutputDto> addCommentToStory(@Valid @PathVariable Long storyId, @RequestBody CommentInputDto commentInputDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CommentOutputDto savedComment = commentService.addComment(commentInputDto, storyId, username);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + savedComment.getId()).toUriString());
        return ResponseEntity.created(uri).body(savedComment);
    }

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isCommentOwner(#commentId)")
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentOutputDto> updateComment(@Valid @PathVariable("commentId") Long commentId, @RequestBody CommentInputDto updatedComment) {
        CommentOutputDto updatedCommentDto = commentService.updateComment(commentId, updatedComment);
        return ResponseEntity.ok().body(updatedCommentDto);
    }

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isCommentOwner(#commentId)")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentOutputDto> getCommentById(@PathVariable("commentId") Long commentId) {
        CommentOutputDto comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok().body(comment);
    }



}
