package com.mf.minutefictionbackend.dtos.inputDtos;

import com.mf.minutefictionbackend.validators.MaxWordCount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StoryInputDto {

    @NotBlank
    @Size(max=50, message = "Title cannot be longer than 50 characters.")
    private String title;
    @NotBlank
    @MaxWordCount(message = "Your story can be no longer than a 100 words.")
    private String content;


}
