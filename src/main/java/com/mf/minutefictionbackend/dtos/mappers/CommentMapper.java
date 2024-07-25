package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.CommentInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.User;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static Comment commentFromInputDtoToModel(CommentInputDto commentInputDto, Story story, User user) {
        Comment comment = new Comment();
        comment.setContent(commentInputDto.getContent());
        comment.setStory(story);
        comment.setUser(user);

        return comment;
    }

    public static CommentOutputDto commentFromModelToOutputDto(Comment comment) {
        CommentOutputDto commentDto = new CommentOutputDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreated(comment.getCreated());

        commentDto.setStoryId(comment.getStory().getId());
        commentDto.setUsername(comment.getUser().getUsername());


        return commentDto;
    }

    public static List<CommentOutputDto> commentModelListToOutputList(List<Comment> comments) {
        if(comments.isEmpty()) {
            throw new ResourceNotFoundException("No comments found.");
        }
        List<CommentOutputDto> commentOutputDtoList = new ArrayList<>();
        comments.forEach((comment) -> commentOutputDtoList.add(commentFromModelToOutputDto(comment)));
        return commentOutputDtoList;
    }

}
