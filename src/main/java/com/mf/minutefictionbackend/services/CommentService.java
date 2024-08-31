package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.CommentInputDto;
import com.mf.minutefictionbackend.dtos.mappers.CommentMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.CommentRepository;
import com.mf.minutefictionbackend.repositories.StoryRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, StoryRepository storyRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
    }


    public CommentOutputDto addComment(CommentInputDto commentInputDto, Long storyId, String username) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("No story found."));
        User user = userRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("No user found."));

        Comment comment = CommentMapper.commentFromInputDtoToModel(commentInputDto);
        comment.setStory(story);
        comment.setUser(user);
        comment.setCreated(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.commentFromModelToOutputDto(savedComment);
    }

    @Transactional
    public CommentOutputDto updateComment(Long storyId, Long commentId, CommentInputDto updatedComment) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("No story found."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("No comment found."));

        if(!comment.getStory().equals(story)) {
            throw new IllegalArgumentException("Comment does not belong to the specific story.");
        }
        comment.setContent(updatedComment.getContent());
        Comment returnComment = commentRepository.save(comment);
        return CommentMapper.commentFromModelToOutputDto(returnComment);
    }


    public void deleteCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("No comment found with id " + commentId));
        commentRepository.delete(comment);
    }


    public CommentOutputDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("No comment found with id " + commentId));
            return CommentMapper.commentFromModelToOutputDto(comment);
    }



    public List<CommentOutputDto> getCommentsByStory(Long storyId) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("No story found for id " + storyId));
            List<Comment> comments = commentRepository.findCommentsByStory(story);
            return CommentMapper.commentModelListToOutputList(comments);
    }


}
