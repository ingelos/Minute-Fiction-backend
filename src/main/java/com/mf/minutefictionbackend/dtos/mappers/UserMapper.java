package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.UpdatePasswordInputDto;
import com.mf.minutefictionbackend.dtos.inputDtos.UpdateUserInputDto;
import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Authority;
import com.mf.minutefictionbackend.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
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

    public static User updateUserFromInputDtoToModel(User existingUser, UpdateUserInputDto userInputDto) {
        existingUser.setEmail(userInputDto.getEmail());
        existingUser.setSubscribedToMailing(userInputDto.isSubscribedToMailing());

        return existingUser;
    }

    public static void updatePasswordFromInputDtoToModel(User existingUser, UpdatePasswordInputDto updatePasswordDto, PasswordEncoder passwordEncoder) {
        if (!updatePasswordDto.getNewPassword().equals(updatePasswordDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        String encodedPassword = passwordEncoder.encode(updatePasswordDto.getNewPassword());
        existingUser.setPassword(encodedPassword);
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
