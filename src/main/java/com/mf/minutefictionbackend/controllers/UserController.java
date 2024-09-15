package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorityInputDto;
import com.mf.minutefictionbackend.dtos.inputDtos.UserInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.BadRequestException;
import com.mf.minutefictionbackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody UserInputDto userInputDto) {
        UserOutputDto createdUser = userService.createUser(userInputDto);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + createdUser.getUsername())
                .toUriString());

        return ResponseEntity.created(uri).body(createdUser);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isOwner(#username)")
    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUserByUsername(@PathVariable("username") String username) {
        UserOutputDto optionalUser = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('EDITOR') or @securityService.isOwner(#username)")
    @PutMapping("/{username}")
    public ResponseEntity<UserOutputDto> updateUser(@Valid @PathVariable("username") String username, @RequestBody UserInputDto userDto) {
        UserOutputDto updatedUser = userService.updateUser(username, userDto);
        return ResponseEntity.ok().body(updatedUser);
    }


    // MANAGE AUTHORITIES

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PostMapping("/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody AuthorityInputDto authorityInputDto) {
        try {
            String authority = authorityInputDto.getAuthority();
            userService.addAuthority(username, authority);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @DeleteMapping("/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }


}



