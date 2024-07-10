package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.mappers.UserMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.mf.minutefictionbackend.dtos.mappers.UserMapper.userFromModelToOutputDto;
import static com.mf.minutefictionbackend.dtos.mappers.UserMapper.userModelSetToOutputSet;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserOutputDto createUser(UserInputDto userInputDto) {
        User user = userRepository.save(UserMapper.userFromInputDtoToModel(userInputDto));
        return userFromModelToOutputDto(user);
    }

    public Set<UserOutputDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return userModelSetToOutputSet(new HashSet<>(allUsers));
    }

    public UserOutputDto getUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isPresent()) {
            return userFromModelToOutputDto(user.get());
        } else {
            throw new ResourceNotFoundException("No user found with username " + username);
        }
    }

    public void deleteUser(String username) {
//        Optional<User> optionalUser = userRepository.findUserByUsername(username);
//        if(optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            userRepository.delete(user);
//            return userFromModelToOutputDto(user);
            if(userRepository.existsById(username)) {
                userRepository.deleteById(username);
        } else {
            throw new ResourceNotFoundException("No user found with username " + username);
        }
    }


}
