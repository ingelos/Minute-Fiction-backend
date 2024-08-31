package com.mf.minutefictionbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mf.minutefictionbackend.enums.StoryStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_sequence")
    @SequenceGenerator(name = "story_sequence", sequenceName = "story_sequence", initialValue = 1004, allocationSize = 1)
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

    @OneToMany(mappedBy = "story", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comments;



    public Long getId() {
        return id;
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

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }


    public AuthorProfile getAuthor() {
        return author;
    }

    public void setAuthor(AuthorProfile author) {
        this.author = author;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Theme getTheme() {
        return theme;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
