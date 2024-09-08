package com.mf.minutefictionbackend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "profile_photos")
@Getter
@Setter
@NoArgsConstructor
public class ProfilePhoto {

    @Id
    private String fileName;

    @OneToOne
    @JoinColumn(name = "author_profile_id")
    private AuthorProfile authorProfile;

    public ProfilePhoto(String fileName) {
        this.fileName = fileName;
    }

}
