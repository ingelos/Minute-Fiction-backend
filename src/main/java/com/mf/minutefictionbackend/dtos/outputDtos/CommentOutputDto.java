package com.mf.minutefictionbackend.dtos.outputDtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentOutputDto {

    private Long id;
    private String content;
    private LocalDateTime created;
    private String username;

}
