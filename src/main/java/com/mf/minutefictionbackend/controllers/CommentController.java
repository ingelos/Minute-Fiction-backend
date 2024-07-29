package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.CommentInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
//@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

//    @PostMapping("/stories/{storyId}/comments")
//    public ResponseEntity<CommentOutputDto> addComment(@PathVariable Long storyId, @RequestParam String username, @RequestBody CommentInputDto commentInputDto) {
//        CommentOutputDto commentDto = commentService.addComment(storyId, username, commentInputDto);
//
//        URI uri = URI.create(ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(commentDto.getId())
//                .toUriString());
//
//        return ResponseEntity.created(uri).body(commentDto);
//    }




    @PostMapping("/stories/{storyId}/comments")
    public ResponseEntity<CommentOutputDto> addComment(@PathVariable Long storyId, @RequestParam String username, @RequestBody CommentInputDto commentInputDto) {
        CommentOutputDto savedComment = commentService.addComment(commentInputDto, storyId, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @PutMapping("/stories/{storyId}/comments/{commentId}")
    public ResponseEntity<CommentOutputDto> updateComment(@PathVariable("storyId") Long storyId, @PathVariable("commentId") Long commentId, @RequestBody CommentInputDto updatedComment) {
        CommentOutputDto updatedCommentDto = commentService.updateComment(storyId, commentId, updatedComment);
        return ResponseEntity.ok().body(updatedCommentDto);
    }
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
