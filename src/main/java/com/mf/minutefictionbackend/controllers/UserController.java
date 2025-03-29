package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.dtos.AuthorityDto;
import com.mf.minutefictionbackend.dtos.dtos.UpdateEmailDto;
import com.mf.minutefictionbackend.dtos.dtos.UpdatePasswordDto;
import com.mf.minutefictionbackend.dtos.dtos.UpdateSubscriptionDto;
import com.mf.minutefictionbackend.dtos.inputDtos.*;
import com.mf.minutefictionbackend.dtos.outputDtos.UserOutputDto;
import com.mf.minutefictionbackend.exceptions.BadRequestException;
import com.mf.minutefictionbackend.services.SecurityService;
import com.mf.minutefictionbackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.List;

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
        UserOutputDto createdUser = userService.createUser(userInputDto);

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + createdUser.getUsername())
                .toUriString());

        return ResponseEntity.created(uri).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        securityService.checkIsEditor();
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserOutputDto> getUserByUsername(@PathVariable("username") String username) {
        securityService.checkIsEditorOrOwner(username);
        UserOutputDto optionalUser = userService.getUserByUsername(username);
        return ResponseEntity.ok().body(optionalUser);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        securityService.checkIsEditorOrOwner(username);
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{username}/email")
    public ResponseEntity<UserOutputDto> updateEmail(@PathVariable("username") String username, @Valid @RequestBody UpdateEmailDto updateEmailDto) {
        securityService.checkIsOwner(username);
        boolean isVerified = userService.verifyPassword(username, updateEmailDto.getCurrentPassword());
        if (!isVerified) {
            throw new BadRequestException("Invalid password");
        }
        UserOutputDto updatedUser = userService.updateEmail(username, updateEmailDto);
        return ResponseEntity.ok().body(updatedUser);
    }

    @PatchMapping("/{username}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable("username") String username, @Valid @RequestBody UpdatePasswordDto updatePasswordDto) {
        securityService.checkIsOwner(username);
        userService.updatePassword(username, updatePasswordDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{username}/subscription")
    public ResponseEntity<Void> updateSubscription( @PathVariable("username") String username, @Valid @RequestBody UpdateSubscriptionDto updateSubscriptionDto) {
        securityService.checkIsOwner(username);
        userService.updateSubscription(username, updateSubscriptionDto);
        return ResponseEntity.noContent().build();
    }

    // MANAGE AUTHORITIES

    @GetMapping("/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        securityService.checkIsEditor();
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping("/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody AuthorityDto authorityDto) {
        securityService.checkIsEditor();
        try {
            String authority = authorityDto.getAuthority();
            userService.addAuthority(username, authority);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping("/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
        securityService.checkIsEditor();
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }


}



