package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.mappers.UserMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.exceptions.UsernameNotFoundException;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;
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

    public void deleteUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        if (user.getAuthorProfile() != null) {
            authorProfileRepository.delete(user.getAuthorProfile());
        }
        userRepository.delete(user);
    }


    public UserOutputDto updateUser(String username, UserOutputDto updatedUser) {
        User updateUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with username " + username));

        updateUser.setUsername(updatedUser.getUsername());
        updateUser.setEmail(updateUser.getEmail());
        updateUser.setSubscribedToMailing(updatedUser.getSubscribedToMailing());

        User returnUser = userRepository.save(updateUser);
        return UserMapper.userFromModelToOutputDto(returnUser);
    }


    // setAuthorities

    // addAuthorities

    // removeAuthorities

}
