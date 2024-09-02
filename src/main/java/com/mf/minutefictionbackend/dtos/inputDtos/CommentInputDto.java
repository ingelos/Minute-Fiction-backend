package com.mf.minutefictionbackend.dtos.inputDtos;

import com.mf.minutefictionbackend.validators.MaxWordCount;
import lombok.Data;

@Data
public class CommentInputDto {

    @MaxWordCount(message= "Your comment can't be longer than the story! Keep it under a 100 words.")
    private String content;


}
