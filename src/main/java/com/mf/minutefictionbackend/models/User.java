package com.mf.minutefictionbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Setter(AccessLevel.NONE)
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
    @JsonIgnore
    private AuthorProfile authorProfile;


    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;

    public User(String username, String password, String email, boolean subscribedToMailing) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.subscribedToMailing = subscribedToMailing;
    }
}
