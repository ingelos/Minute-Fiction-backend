package com.mf.minutefictionbackend.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String status;
    @Column
    private LocalDate publishDate;


    @ManyToOne
    @JoinColumn(name = "author_profile_username", referencedColumnName = "username")
    private AuthorProfile authorProfile;


    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @OneToMany(mappedBy = "story")
    private List<Comment> comments = new ArrayList<>();



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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public AuthorProfile getAuthorProfile() {
        return authorProfile;
    }

    public Theme getTheme() {
        return theme;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
