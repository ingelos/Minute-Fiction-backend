package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findByAuthorProfile_Username(String username);


}
