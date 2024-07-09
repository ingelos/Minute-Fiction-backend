package com.mf.minutefictionbackend.dtos.inputDtos;

import jakarta.validation.constraints.Size;

public class StoryInputDto {

    @Size(max=50, message = "Title must be under 50 characters")
    public String title;
    @Size(min=10, max=500, message = "Story must be under a 100 words")
    public String content;


}
