package com.mf.minutefictionbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
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
