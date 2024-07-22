package com.mf.minutefictionbackend.dtos.inputDtos;

import com.mf.minutefictionbackend.validators.MaxWordCount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StoryInputDto {

    @NotBlank
    @Size(max=50, message = "Title must be under 50 characters")
    public String title;
    @NotBlank
    @MaxWordCount(message = "Story has to have no more than a 100 words!")
    public String content;
    @NotNull
    public Long themeId;



    public StoryInputDto(String title, String content, Long themeId) {
        this.title = title;
        this.content = content;
        this.themeId = themeId;
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

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }
}
