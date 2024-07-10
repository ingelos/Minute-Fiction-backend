package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.CommentInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mf.minutefictionbackend.dtos.mappers.CommentMapper.*;


@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public CommentOutputDto createComment(CommentInputDto commentInputDto) {
            Comment comment = commentRepository.save(commentFromInputDtoToModel(commentInputDto));
            return commentFromModelToOutputDto(comment);
    }

    public List<CommentOutputDto> getAllCommentsOnStory(Long id) {
        List<Comment> comments = commentRepository.findAllCommentsOnStory(id);
        if(!comments.isEmpty()) {
            return commentModelListToOutputList(comments);
        } else {
            throw new ResourceNotFoundException("No comments found for on story " + id);
        }
    }




}
