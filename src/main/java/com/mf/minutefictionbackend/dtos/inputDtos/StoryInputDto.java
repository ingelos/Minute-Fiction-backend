package com.mf.minutefictionbackend.dtos.inputDtos;

import com.mf.minutefictionbackend.validators.MaxWordCount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StoryInputDto {

    @NotBlank
    @Size(max=50, message = "Title must be under 50 characters")
    public String title;
    @NotBlank
    @MaxWordCount(message = "Your story can be a maximum of a 100 words!")
    public String content;




    public StoryInputDto() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
