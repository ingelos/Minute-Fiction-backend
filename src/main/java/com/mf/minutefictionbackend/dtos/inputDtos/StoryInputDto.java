package com.mf.minutefictionbackend.dtos.inputDtos;

import com.mf.minutefictionbackend.annotations.MaxWordCount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StoryInputDto {

    @NotBlank
    @Size(max=50, message = "Title must be under 50 characters")
    public String title;
    @NotBlank
    @MaxWordCount(message = "Story has to have no more than a 100 words!")
    public String content;


}
