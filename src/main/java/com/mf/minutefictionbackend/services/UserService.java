package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.mappers.UserMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.exceptions.UsernameAlreadyExistsException;
import com.mf.minutefictionbackend.exceptions.UsernameNotFoundException;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorProfileRepository authorProfileRepository;

    public UserService(UserRepository userRepository, AuthorProfileRepository authorProfileRepository) {
        this.userRepository = userRepository;
        this.authorProfileRepository = authorProfileRepository;
    }

    public UserOutputDto createUser(UserInputDto userInputDto) {
        if(userRepository.existsById(userInputDto.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already taken, try another.");
        }
        User user = userRepository.save(UserMapper.userFromInputDtoToModel(userInputDto));
        return UserMapper.userFromModelToOutputDto(user);
    }

    public Set<UserOutputDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return UserMapper.userModelSetToOutputSet(new HashSet<>(allUsers));
    }

    public UserOutputDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
            return UserMapper.userFromModelToOutputDto(user);
    }

    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        if (user.getAuthorProfile() != null) {
            authorProfileRepository.delete(user.getAuthorProfile());
        }
        userRepository.delete(user);
    }


    @Transactional
    public UserOutputDto updateUser(String username, UserInputDto updatedUser) {
        User updateUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with username " + username));

        if(updatedUser.getUsername() != null) {
            throw new IllegalArgumentException("Username cannot be changed.");
        }
        if(updatedUser.getEmail() != null) {
            updateUser.setEmail(updatedUser.getEmail());
        }
        if(updatedUser.getIsSubscribedToMailing() != null) {
            updateUser.setSubscribedToMailing(updatedUser.getIsSubscribedToMailing());
        }
        if(updatedUser.getPassword() != null) {
            updateUser.setPassword(updatedUser.getPassword());
        }

        User returnUser = userRepository.save(updateUser);
        return UserMapper.userFromModelToOutputDto(returnUser);
    }


    // setAuthorities

    // addAuthorities

    // removeAuthorities

}
