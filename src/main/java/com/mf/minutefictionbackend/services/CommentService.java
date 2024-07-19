package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.CommentInputDto;
import com.mf.minutefictionbackend.dtos.mappers.CommentMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.exceptions.UsernameNotFoundException;
import com.mf.minutefictionbackend.models.Comment;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.CommentRepository;
import com.mf.minutefictionbackend.repositories.StoryRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final StoryRepository storyRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, StoryRepository storyRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
    }


    public CommentOutputDto addComment(Long storyId,  String username, CommentInputDto commentInputDto) {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            Optional<Story> optionalStory = storyRepository.findById(storyId);

            if(optionalUser.isEmpty() || optionalStory.isEmpty()) {
                throw new ResourceNotFoundException("User or story not found");
            }

            User user = optionalUser.get();
            Story story = optionalStory.get();

            Comment comment = commentRepository.save(CommentMapper.commentFromInputDtoToModel(commentInputDto, user, story));

            return CommentMapper.commentFromModelToOutputDto(comment);
    }



    public void deleteCommentById(Long storyId, Long commentId) {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalStory.isPresent() && optionalComment.isPresent()) {
            commentRepository.deleteById(commentId);
        } else throw new ResourceNotFoundException("No comment found.");
    }

    public List<CommentOutputDto> getCommentsByStory(Long storyId) {
       Optional<Story> optionalStory = storyRepository.findById(storyId);
       if(optionalStory.isEmpty()) {
            throw new ResourceNotFoundException();
       } else {
           List<Comment> comments = commentRepository.findCommentsByStory(storyId);
           return CommentMapper.commentModelListToOutputList(comments);
           }
       }


    public CommentOutputDto getCommentById(Long storyId, Long commentId) {
        Optional<Story> optionalStory = storyRepository.findById(storyId);
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isPresent() && optionalStory.isPresent()) {
            return CommentMapper.commentFromModelToOutputDto(optionalComment.get());
        } else throw new ResourceNotFoundException("No comment found with id " + commentId);
    }

}
