package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}
