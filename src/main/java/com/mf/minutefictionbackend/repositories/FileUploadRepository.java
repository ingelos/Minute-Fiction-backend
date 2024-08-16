package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.ProfilePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<ProfilePhoto, String> {

    Optional<ProfilePhoto> findByFileName(String fileName);

}
