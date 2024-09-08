package com.mf.minutefictionbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@NoArgsConstructor
public class AuthorProfile {

    @Setter(AccessLevel.NONE)
    @Id
    @Column
    private String username;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String bio;
    @Column
    private LocalDate dob;

    @OneToOne
    @MapsId
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "authorProfile", optional = true)
    private ProfilePhoto profilePhoto;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Story> stories = new ArrayList<>();



}
