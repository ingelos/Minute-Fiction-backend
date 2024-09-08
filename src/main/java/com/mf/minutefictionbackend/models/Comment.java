package com.mf.minutefictionbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", initialValue = 1004, allocationSize = 1)
    private Long id;
    @Column
    private String content;
    @Column
    private LocalDateTime created;


    @ManyToOne
    @JoinColumn(name = "story_id")
    @JsonIgnore
    private Story story;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    @PrePersist
    public void onPrePersist() {
        this.setCreated(LocalDateTime.now());
    }




}
