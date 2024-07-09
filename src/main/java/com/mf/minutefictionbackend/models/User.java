package com.mf.minutefictionbackend.models;

import jakarta.persistence.*;

import java.util.ArrayList;
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




    @OneToOne(mappedBy = "user")
    private AuthorProfile authorProfile;

    @ManyToOne
    @JoinColumn(name = "mailing_id")
    private Mailing mailing;

    @OneToMany(mappedBy = "user")
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

    public AuthorProfile getAuthorProfile() {
        return authorProfile;
    }

    public Mailing getMailing() {
        return mailing;
    }
}
