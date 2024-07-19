package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.models.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {

    public static User userFromInputDtoToModel(UserInputDto userInputDto) {
        User user = new User();
        user.setUsername(userInputDto.username);
        user.setPassword(userInputDto.password);
        user.setEmail(userInputDto.email);
        user.setSubscribedToMailing(userInputDto.subscribedToMailing);

        return user;
    }

    public static UserOutputDto userFromModelToOutputDto(User user) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setUsername(user.getUsername());
        userOutputDto.setPassword(user.getPassword());
        userOutputDto.setEmail(user.getEmail());
        userOutputDto.setSubscribedToMailing(user.getSubscribedToMailing());

        return userOutputDto;
    }

    public static Set<UserOutputDto> userModelSetToOutputSet(Set<User> users) {
        Set<UserOutputDto> userOutputDtoSet = new HashSet<>();

        for(User user : users) {
            userOutputDtoSet.add(userFromModelToOutputDto(user));
        }
        return userOutputDtoSet;
    }
    



}
