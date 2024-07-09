package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
