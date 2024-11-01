package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.repositories.CommentRepository;
import com.mf.minutefictionbackend.repositories.StoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class SecurityService {

    private final StoryRepository storyRepository;
    private final CommentRepository commentRepository;


    public SecurityService(StoryRepository storyRepository, CommentRepository commentRepository) {
        this.storyRepository = storyRepository;
        this.commentRepository = commentRepository;
    }


    public boolean isOwner(String username) {
        String currentUsername = getCurrentUsername();
        return username.equals(currentUsername);
    }

    public boolean isAuthor(Long storyId) {
        String currentUsername = getCurrentUsername();
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found."));
        return story.getAuthor().getUsername().equals(currentUsername);
    }

    public boolean isCommentOwner(Long commentId) {
        String currentUsername = getCurrentUsername();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found."));
        return comment.getUser().getUsername().equals(currentUsername);
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }


}
