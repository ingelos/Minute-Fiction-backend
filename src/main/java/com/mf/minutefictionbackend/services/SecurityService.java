package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.repositories.CommentRepository;
import com.mf.minutefictionbackend.repositories.StoryRepository;
import org.springframework.security.access.AccessDeniedException;
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

    public void checkIsAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required. Please log in.");
        }
    }

    public void checkIsOwner(String username) {
        String currentUsername = getCurrentUsername();
        if (!username.equals(currentUsername)) {
            throw new AccessDeniedException("You are not authorized to perform this action.");
        }
    }

    public void checkIsAuthor() {
        boolean isAuthor = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("AUTHOR"));
        if (!isAuthor) {
            throw new AccessDeniedException("You do not have author permissions.");
        }
    }

    public void checkIsEditorOrCommentOwner(Long commentId) {
        checkIsAuthenticated();

        boolean isEditor = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("EDITOR"));

        if(!isEditor) {
            String currentUsername = getCurrentUsername();
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Comment not found."));
            if(!comment.getUser().getUsername().equals(currentUsername)) {
                throw new AccessDeniedException("You are not authorized to modify this comment");
            }
        }
    }

    public void checkIsEditor() {
        boolean isEditor = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("EDITOR"));
        if(!isEditor) {
            throw new AccessDeniedException("You do not have editor permissions.");
        }
    }

    public void checkIsEditorOrOwner(String username) {
        String currentUsername = getCurrentUsername();
        boolean isEditor = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("EDITOR"));

        if(!isEditor && !currentUsername.equals(username)) {
            throw new AccessDeniedException("You are not authorized to access this resource.");
        }
    }

    public void checkIsEditorOrAuthor(Long storyId) {
        boolean isEditor = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("EDITOR"));

        if(!isEditor) {
            String currentUsername = getCurrentUsername();
            Story story = storyRepository.findById(storyId)
                    .orElseThrow(() -> new ResourceNotFoundException("Story not found."));
            if(!story.getAuthor().getUsername().equals(currentUsername)) {
                throw new AccessDeniedException("You are not authorized to perform this action.");
            }
        }
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Authentication required.");
        }
        return authentication.getName();
    }





}
