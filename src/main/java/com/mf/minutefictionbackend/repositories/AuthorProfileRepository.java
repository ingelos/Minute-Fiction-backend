package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorProfileRepository extends JpaRepository<AuthorProfile, String> {



}
