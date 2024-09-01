package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.services.AuthorProfileService;
import com.mf.minutefictionbackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthorProfileService authorProfileService;

    public UserController(UserService userService, AuthorProfileService authorProfileService) {
        this.userService = userService;

        this.authorProfileService = authorProfileService;
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody UserInputDto userInputDto) {
        UserOutputDto user = userService.createUser(userInputDto);
        // add authority

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + user.getUsername())
                .toUriString());

        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping
    public ResponseEntity<Set<UserOutputDto>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUserByUsername(@PathVariable("username") String username) {
        UserOutputDto optionalUser = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{username}")
    public ResponseEntity<UserOutputDto> updateUser(@Valid @PathVariable("username") String username, @RequestBody UserInputDto userDto) {
        UserOutputDto updatedUser = userService.updateUser(username, userDto);
        return ResponseEntity.ok().body(updatedUser);
    }




}



