package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Authority;
import com.mf.minutefictionbackend.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserMapper {

    public static User userFromInputDtoToModel(UserInputDto userInputDto, String encodedPassword) {
        Set<Authority> authorities = AuthorityMapper.fromDtoToSet(userInputDto.getAuthorities());
        return new User(
                userInputDto.getUsername(),
                encodedPassword,
                userInputDto.getEmail(),
                userInputDto.isSubscribedToMailing(),
                authorities
        );
    }

    public static UserOutputDto userFromModelToOutputDto(User user) {
        UserOutputDto userDto = new UserOutputDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setSubscribedToMailing(user.isSubscribedToMailing());
        userDto.setHasAuthorProfile(user.getAuthorProfile() != null);
        userDto.setAuthorities(user.getAuthorities());
        return userDto;
    }

    public static List<UserOutputDto> userModelListToOutputList(List<User> users) {
        if(users.isEmpty()) {
            throw new ResourceNotFoundException("No users found.");
        }
        List<UserOutputDto> userOutputDtoList = new ArrayList<>();
        users.forEach((user) -> userOutputDtoList.add(userFromModelToOutputDto(user)));
        return userOutputDtoList;
    }


}
