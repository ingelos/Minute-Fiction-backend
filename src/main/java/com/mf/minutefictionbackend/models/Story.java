package com.mf.minutefictionbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mf.minutefictionbackend.enums.StoryStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "stories")
@Getter
@Setter
public class Story {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_sequence")
    @SequenceGenerator(name = "story_sequence", sequenceName = "story_sequence", initialValue = 1006, allocationSize = 1)
    @Column
    private Long id;
    @Column
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column
    private StoryStatus status;
    @Column
    private LocalDate publishDate;


    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "username")
    @JsonIgnore
    private AuthorProfile author;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    @JsonIgnore
    private Theme theme;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "story")
    @JsonIgnore
    private List<Comment> comments;



}
