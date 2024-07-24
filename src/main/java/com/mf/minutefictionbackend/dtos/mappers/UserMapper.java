package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {

    public static User userFromInputDtoToModel(UserInputDto userInputDto) {
        User user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());
        user.setEmail(userInputDto.getEmail());
        user.setSubscribedToMailing(userInputDto.getSubscribedToMailing());

        return user;
    }

    public static UserOutputDto userFromModelToOutputDto(User user) {
        UserOutputDto userDto = new UserOutputDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setSubscribedToMailing(user.getSubscribedToMailing());
        userDto.setAuthorProfile(AuthorProfileMapper.authorProfileFromModelToOutputDto(user.getAuthorProfile()));

        return userDto;
    }

    public static Set<UserOutputDto> userModelSetToOutputSet(Set<User> users) {
        if(users.isEmpty()) {
            throw new ResourceNotFoundException("No users found.");
        }

        Set<UserOutputDto> userOutputDtoSet = new HashSet<>();

        for(User user : users) {
            userOutputDtoSet.add(userFromModelToOutputDto(user));
        }
        return userOutputDtoSet;
    }
    



}
