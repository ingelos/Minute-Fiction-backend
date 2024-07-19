package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.CommentInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.User;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static Comment commentFromInputDtoToModel(CommentInputDto commentInputDto, User user, Story story) {
        Comment comment = new Comment();
        comment.setContent(commentInputDto.getContent());
        comment.setUser(user);
        comment.setStory(story);

        return comment;
    }

    public static CommentOutputDto commentFromModelToOutputDto(Comment comment) {
        CommentOutputDto commentOutputDto = new CommentOutputDto();
        commentOutputDto.setId(comment.getId());
        commentOutputDto.setContent(comment.getContent());
        commentOutputDto.setCreated(comment.getCreated());
        commentOutputDto.setUser(UserMapper.userFromModelToOutputDto(comment.getUser()));
        commentOutputDto.setStory(StoryMapper.storyFromModelToOutputDto(comment.getStory()));

        return commentOutputDto;
    }

    public static List<CommentOutputDto> commentModelListToOutputList(List<Comment> comments) {
        List<CommentOutputDto> commentOutputDtoList = new ArrayList<>();

        for(Comment comment : comments) {
            commentOutputDtoList.add(commentFromModelToOutputDto(comment));
        }
        return commentOutputDtoList;
    }

}
