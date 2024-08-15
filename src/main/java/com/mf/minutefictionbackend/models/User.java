package com.mf.minutefictionbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, length = 255)
    private String password;
    @Column
    private String email;
    @Column
    private boolean subscribedToMailing;


    @OneToOne(mappedBy = "user", optional = true)
//    @JsonIgnore
    private AuthorProfile authorProfile;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSubscribedToMailing() {
        return subscribedToMailing;
    }

    public void setSubscribedToMailing(boolean subscribedToMailing) {
        this.subscribedToMailing = subscribedToMailing;
    }

    public AuthorProfile getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(AuthorProfile authorProfile) {
        this.authorProfile = authorProfile;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
