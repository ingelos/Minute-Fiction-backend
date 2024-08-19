package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findByStatus(StoryStatus status);
    List<Story> findByAuthor_UsernameAndStatus(String username, StoryStatus status);
    List<Story> findByStatusAndTheme(StoryStatus status, Theme theme);

}
