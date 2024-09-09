package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.mappers.UserMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.BadRequestException;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.services.SecurityService;
import com.mf.minutefictionbackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;

        this.securityService = securityService;
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody UserInputDto userInputDto) {
        User createdUser = userService.createUser(userInputDto);
        UserOutputDto userOutputDto = UserMapper.userFromModelToOutputDto(createdUser);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()

                .path("/" + userOutputDto.getUsername())
                .toUriString());

        return ResponseEntity.created(uri).body(userOutputDto);
    }

    @GetMapping
    public ResponseEntity<Set<UserOutputDto>> getAllUsers() {
        if(!securityService.isEditor()) {
            throw new AccessDeniedException("You do not have permission to perform this action.");
        }
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUserByUsername(@PathVariable("username") String username) {
        if(!securityService.isOwnerOrEditor(username)) {
            throw new AccessDeniedException("You do not have permission to see this users data.");
        }
        UserOutputDto optionalUser = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        if(!securityService.isOwnerOrEditor(username)) {
            throw new AccessDeniedException("You do not have permission to delete this user.");
        }
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserOutputDto> updateUser(@Valid @PathVariable("username") String username, @RequestBody UserInputDto userDto) {
        if(!securityService.isOwner(username)) {
            throw new AccessDeniedException("You do not have permission to update this users data.");
        }
        UserOutputDto updatedUser = userService.updateUser(username, userDto);
        return ResponseEntity.ok().body(updatedUser);
    }


    // MANAGE AUTHORITIES

    @GetMapping("/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        if(!securityService.isEditor()) {
            throw new AccessDeniedException("You do not have permission to request authorities.");
        }
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping("/{username}/authority")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody String authority) {
        if(!securityService.isEditor()) {
            throw new AccessDeniedException("You do not have permission to add authorities.");
        }
        try {
            userService.addAuthority(username, authority);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping("/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        if(!securityService.isEditor()) {
            throw new AccessDeniedException("You do not have permission to delete authorities.");
        }
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }


}



