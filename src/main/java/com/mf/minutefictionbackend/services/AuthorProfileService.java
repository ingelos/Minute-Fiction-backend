package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.mappers.AuthorProfileMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.exceptions.*;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.ProfilePhoto;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import com.mf.minutefictionbackend.repositories.FileUploadRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorProfileService {

    private final AuthorProfileRepository authorProfileRepository;
    private final UserRepository userRepository;
    private final PhotoService photoService;
    private final FileUploadRepository fileUploadRepository;
    private final UserService userService;



    public AuthorProfileService(AuthorProfileRepository authorProfileRepository, UserRepository userRepository, PhotoService photoService, FileUploadRepository fileUploadRepository, UserService userService) {
        this.authorProfileRepository = authorProfileRepository;
        this.userRepository = userRepository;
        this.photoService = photoService;
        this.fileUploadRepository = fileUploadRepository;
        this.userService = userService;
    }

    @Transactional
    public AuthorProfileOutputDto createAuthorProfile(String username, AuthorProfileInputDto authorProfileInputDto) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (user.getAuthorProfile() != null) {
            throw new BadRequestException("Author profile already exists for this user.");
        }
        AuthorProfile authorProfile = AuthorProfileMapper.authorProfileFromInputDtoToModel(authorProfileInputDto);
        authorProfile.setUser(user);

        AuthorProfile savedProfile = authorProfileRepository.save(authorProfile);
        user.setAuthorProfile(savedProfile);
        userService.addAuthority(username, "AUTHOR");
        userRepository.save(user);

        return AuthorProfileMapper.authorProfileFromModelToOutputDto(savedProfile);
    }

    public List<AuthorProfileOutputDto> getAllAuthorProfiles() {
        List<AuthorProfile> allAuthorProfiles = authorProfileRepository.findAllByOrderByLastnameAsc();
        return AuthorProfileMapper.authorProfileModelListToOutputList(allAuthorProfiles);
    }

    public AuthorProfileOutputDto getAuthorProfileByUsername(String username) {
        AuthorProfile authorProfile = authorProfileRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("No author profile found for user with username " + username));
        return AuthorProfileMapper.authorProfileFromModelToOutputDto(authorProfile);
    }

    @Transactional
    public AuthorProfileOutputDto updateAuthorProfile(String username, AuthorProfileInputDto updatedProfile) {
        AuthorProfile updateProfile = authorProfileRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("No author profile found for username " + username));

        updateProfile.setFirstname(updatedProfile.getFirstname());
        updateProfile.setLastname(updatedProfile.getLastname());
        updateProfile.setBio(updatedProfile.getBio());
        updateProfile.setDob(updatedProfile.getDob());

        AuthorProfile returnAuthorProfile = authorProfileRepository.save(updateProfile);
        return AuthorProfileMapper.authorProfileFromModelToOutputDto(returnAuthorProfile);
    }

    @Transactional
    public void deleteAuthorProfile(String username) {
        AuthorProfile authorProfile = authorProfileRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User " + username + " has no author profile."));

        if (!authorProfile.getStories().isEmpty()) {
            throw new IllegalArgumentException("Author has stories linked to profile. Delete stories first.");
        }

        if (authorProfile.getProfilePhoto() != null) {
            ProfilePhoto photo = authorProfile.getProfilePhoto();
            authorProfile.setProfilePhoto(null);
            fileUploadRepository.delete(photo);
            photoService.deleteFile(photo.getFileName());
        }

        authorProfileRepository.deleteById(username);

        User user = userRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        user.setAuthorProfile(null);
        userService.removeAuthority(username, "AUTHOR");
        userRepository.save(user);
    }


    // MANAGE PROFILE PHOTOS

    @Transactional
    public Resource getPhotoForAuthorProfile(String username) {
        AuthorProfile authorProfile = authorProfileRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("No author profile found for user with username " + username));
        ProfilePhoto profilePhoto = authorProfile.getProfilePhoto();
        if (profilePhoto == null) {
            throw new ResourceNotFoundException("Author " + username + " has no photo.");
        }
        return photoService.downLoadFile(profilePhoto.getFileName());
    }


    @Transactional
    public AuthorProfileOutputDto assignPhotoToAuthorProfile(String fileName, String username) {
        AuthorProfile authorProfile = authorProfileRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("No author profile found for user with username " + username));

        ProfilePhoto currentPhoto = authorProfile.getProfilePhoto();
        ProfilePhoto newPhoto = fileUploadRepository.findByFileName(fileName)
                .orElse(null);

        if (newPhoto == null) {
            newPhoto = new ProfilePhoto();
            newPhoto.setFileName(fileName);
        }

        if (currentPhoto != null && !currentPhoto.getFileName().equals(fileName)) {
            authorProfile.setProfilePhoto(null);
            fileUploadRepository.delete(currentPhoto);
        }

        newPhoto.setAuthorProfile(authorProfile);
        fileUploadRepository.save(newPhoto);

        authorProfile.setProfilePhoto(newPhoto);
        authorProfileRepository.save(authorProfile);

        return AuthorProfileMapper.authorProfileFromModelToOutputDto(authorProfile);
    }

    @Transactional
    public void deletePhotoByUsername(String username) {
        AuthorProfile authorProfile = authorProfileRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("No author profile found for " + username));
        ProfilePhoto photo = authorProfile.getProfilePhoto();
        if(photo == null) {
            throw new ResourceNotFoundException("No profile photo found for this author.");
        }
        authorProfile.setProfilePhoto(null);
        authorProfileRepository.save(authorProfile);
        fileUploadRepository.delete(photo);
        photoService.deleteFile(photo.getFileName());
    }
}


