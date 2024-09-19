package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.mappers.UserMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.exceptions.UsernameAlreadyExistsException;
import com.mf.minutefictionbackend.exceptions.UsernameNotFoundException;
import com.mf.minutefictionbackend.models.Authority;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorProfileRepository authorProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthorProfileRepository authorProfileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorProfileRepository = authorProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserOutputDto createUser(UserInputDto userInputDto) {
        if(userRepository.existsByUsername(userInputDto.getUsername())) {
            throw new UsernameAlreadyExistsException("Username is already taken, try another.");
        }

        String encodedPassword = passwordEncoder.encode(userInputDto.getPassword());
        User user = UserMapper.userFromInputDtoToModel(userInputDto, encodedPassword);

        Authority defaultAuthority = new Authority("READER");
        user.getAuthorities().add(defaultAuthority);

        User savedUser = userRepository.save(user);
        return UserMapper.userFromModelToOutputDto(savedUser);
    }


    public List<UserOutputDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return UserMapper.userModelListToOutputList(allUsers);
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
        user.getAuthorities().clear();
        userRepository.delete(user);
    }


    @Transactional
    public UserOutputDto updateUser(String username, UserInputDto updatedUser) {
        User updateUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with username " + username));

        if(updatedUser.getUsername() != null) {
            throw new IllegalArgumentException("Username cannot be changed.");
        }

        updateUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        updateUser.setEmail(updatedUser.getEmail());
        updateUser.setSubscribedToMailing(updatedUser.isSubscribedToMailing());

        User savedUser = userRepository.save(updateUser);

        return UserMapper.userFromModelToOutputDto(savedUser);
    }


    // internal method for authentication

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }


    // MANAGING AUTHORITIES





    public List<String> getAuthorities(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        UserOutputDto userDto = UserMapper.userFromModelToOutputDto(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        user.addAuthority(new Authority(authority));
        userRepository.save(user);
    }


    public void removeAuthority(String username, String authority) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Authority authorityToRemove = user.getAuthorities().stream()
                .filter((a) -> a.getAuthority().equalsIgnoreCase(authority))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Authority " + authority + " not found for this user."));
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }


}
