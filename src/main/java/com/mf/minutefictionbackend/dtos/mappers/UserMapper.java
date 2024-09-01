package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {

    public static User userFromInputDtoToModel(UserInputDto userInputDto) {
        return new User(
                userInputDto.getUsername(),
                userInputDto.getPassword(),
                userInputDto.getEmail(),
                userInputDto.getIsSubscribedToMailing()
        );
    }

    public static UserOutputDto userFromModelToOutputDto(User user) {
        UserOutputDto userDto = new UserOutputDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setIsSubscribedToMailing(user.isSubscribedToMailing());
        userDto.setHasAuthorProfile(user.getAuthorProfile() != null);

        return userDto;
    }

    public static Set<UserOutputDto> userModelSetToOutputSet(Set<User> users) {
        if(users.isEmpty()) {
            throw new ResourceNotFoundException("No users found.");
        }
        Set<UserOutputDto> userOutputDtoSet = new HashSet<>();
        users.forEach((user) -> userOutputDtoSet.add(userFromModelToOutputDto(user)));
        return userOutputDtoSet;
    }


}
