package com.mf.minutefictionbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ProfilePhoto {

    @Id
    private String fileName;

    @OneToOne
    @JoinColumn(name = "author_profile_id")
    private AuthorProfile authorProfile;

    public ProfilePhoto(String fileName) {
        this.fileName = fileName;
    }

    public ProfilePhoto() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public AuthorProfile getAuthorProfile() {
        return authorProfile;
    }

    public void setAuthorProfile(AuthorProfile authorProfile) {
        this.authorProfile = authorProfile;
    }
}
