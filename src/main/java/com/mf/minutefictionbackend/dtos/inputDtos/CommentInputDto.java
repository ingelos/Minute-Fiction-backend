package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.Size;

public class CommentInputDto {

    @Size(min=3, max=500, message= "Your comment must be under a 100 words")
    public String comment;

}
