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
import java.util.Optional;
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
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return UserMapper.userFromModelToOutputDto(user.get());
        } else {
            throw new ResourceNotFoundException("No user found with username " + username);
        }
    }

    public void deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findById(username);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (user.getAuthorProfile() != null) {
                    authorProfileRepository.delete(user.getAuthorProfile());
                }
                userRepository.delete(user);
        } else {
            throw new ResourceNotFoundException("No user found with username " + username);
        }
    }


    public void updateUser(String username, UserOutputDto updatedUser) {
        Optional<User> u = userRepository.findByUsername(username);
        if (u.isPresent()) {
            User updateUser = u.get();
            updateUser.setPassword(updatedUser.getPassword());
            updateUser.setUsername(updatedUser.getUsername());
            updateUser.setEmail(updatedUser.getEmail());
            updateUser.setSubscribedToMailing(updatedUser.getSubscribedToMailing());

            User returnUser = userRepository.save(updateUser);
            UserMapper.userFromModelToOutputDto(returnUser);
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    // setAuthorities
    // addAuthorities
    // removeAuthorities

}
