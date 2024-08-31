package com.mf.minutefictionbackend.dtos.inputDtos;

import com.mf.minutefictionbackend.validators.MaxWordCount;

public class CommentInputDto {

    @MaxWordCount(message= "Your comment can't be longer than the story! Max a 100 words!")
    public String content;


    public CommentInputDto() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
