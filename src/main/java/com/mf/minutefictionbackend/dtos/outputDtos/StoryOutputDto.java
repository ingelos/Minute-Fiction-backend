package com.mf.minutefictionbackend.dtos.outputDtos;

import com.mf.minutefictionbackend.enums.StoryStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StoryOutputDto {

    private Long id;
    private String title;
    private String content;
    private StoryStatus status;
    private LocalDate publishDate;

    private String username;
    private String authorFirstname;
    private String authorLastname;
    private String themeName;

}
