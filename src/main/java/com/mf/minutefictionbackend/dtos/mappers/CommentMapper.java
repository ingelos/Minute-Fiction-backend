package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.CommentInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.models.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static Comment commentFromInputDtoToModel(CommentInputDto commentInputDto) {
        Comment comment = new Comment();
        comment.setContent(comment.getContent());

        return comment;
    }

    public static CommentOutputDto commentFromModelToOutputDto(Comment comment) {
        CommentOutputDto commentOutputDto = new CommentOutputDto();
        commentOutputDto.setId(comment.getId());
        commentOutputDto.setContent(comment.getContent());
        commentOutputDto.setDate(comment.getDate());

        return commentOutputDto;
    }

    public static List<CommentOutputDto> commentModelListToOutputList(List<Comment> comments) {
        List<CommentOutputDto> commentOutputDtoList = new ArrayList<>();

        comments.forEach((comment) -> commentOutputDtoList.add(commentFromModelToOutputDto(comment)));
        return commentOutputDtoList;
    }

}
