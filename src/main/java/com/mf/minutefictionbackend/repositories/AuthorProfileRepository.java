package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorProfileRepository extends JpaRepository<AuthorProfile, Long> {




}
