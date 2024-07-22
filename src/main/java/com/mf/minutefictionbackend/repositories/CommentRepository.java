package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByStory_Id(Long storyId);
}
