package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.enums.StoryStatus;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findByStatusOrderByPublishDateDesc(StoryStatus status);
    List<Story> findByAuthor_UsernameAndStatus(String username, StoryStatus status);
    List<Story> findByAuthor_Username(String username);
    List<Story> findByStatusAndTheme(StoryStatus status, Theme theme);
    List<Story> findByThemeId(Long themeId);
    Optional<Story> findByStatusAndId(StoryStatus status, Long storyId);
    boolean existsByThemeAndAuthorUsername(Theme theme, String username);
    int countSubmissionsByTheme(Theme theme);

}
