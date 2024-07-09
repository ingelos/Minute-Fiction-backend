package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.AuthorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorProfileRepository extends JpaRepository<AuthorProfile, Long> {
}
