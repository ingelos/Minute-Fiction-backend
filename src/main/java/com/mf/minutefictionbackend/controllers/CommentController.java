package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.CommentInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/stories/{storyId}/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentOutputDto> addComment(@PathVariable Long storyId, @RequestParam String username, @RequestBody CommentInputDto commentInputDto) {
        CommentOutputDto commentDto = commentService.addComment(storyId, username, commentInputDto);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(commentDto.getId())
                .toUriString());

        return ResponseEntity.created(uri).body(commentDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("storyId") Long storyId, @PathVariable("commentId") Long commentId) {
        commentService.deleteCommentById(storyId, commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentOutputDto> getCommentById(@PathVariable("storyId") Long storyId, @PathVariable("commentId") Long commentId) {
        CommentOutputDto comment = commentService.getCommentById(storyId, commentId);
        return ResponseEntity.ok().body(comment);
    }

    @GetMapping
    public ResponseEntity<List<CommentOutputDto>> getCommentsByStory(@PathVariable Long storyId) {
        List<CommentOutputDto> comments = commentService.getCommentsByStory(storyId);
        return ResponseEntity.ok(comments);
    }


}
