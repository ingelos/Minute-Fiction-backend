package com.mf.minutefictionbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", initialValue = 1003, allocationSize = 1)
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



    public Comment() {
        this.created = LocalDateTime.now();
    }



    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Story getStory() {
        return story;
    }

    public User getUser() {
        return user;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
