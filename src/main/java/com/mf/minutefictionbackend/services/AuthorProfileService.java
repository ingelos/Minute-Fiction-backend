package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.mappers.AuthorProfileMapper;
import com.mf.minutefictionbackend.dtos.mappers.ProfilePhotoMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.exceptions.AuthorProfileDeletionException;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.exceptions.UsernameNotFoundException;
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
import java.util.Optional;


@Service
public class AuthorProfileService {

    private final AuthorProfileRepository authorProfileRepository;
    private final UserRepository userRepository;
    private final PhotoService photoService;
    private final FileUploadRepository fileUploadRepository;


    public AuthorProfileService(AuthorProfileRepository authorProfileRepository, UserRepository userRepository, PhotoService photoService, FileUploadRepository fileUploadRepository) {
        this.authorProfileRepository = authorProfileRepository;
        this.userRepository = userRepository;
        this.photoService = photoService;
        this.fileUploadRepository = fileUploadRepository;
    }

    @Transactional
    public AuthorProfileOutputDto createAuthorProfile(String username, AuthorProfileInputDto authorProfileInputDto) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));

        AuthorProfile authorProfile = AuthorProfileMapper.authorProfileFromInputDtoToModel(authorProfileInputDto);
        authorProfile.setUser(user);

        AuthorProfile savedProfile = authorProfileRepository.save(authorProfile);

        user.setAuthorProfile(savedProfile);
        userRepository.save(user);

        return AuthorProfileMapper.authorProfileFromModelToOutputDto(savedProfile);
    }

    public List<AuthorProfileOutputDto> getAllAuthorProfiles() {
        List<AuthorProfile> allAuthorProfiles = authorProfileRepository.findAll();

        return AuthorProfileMapper.authorProfileModelListToOutputList(allAuthorProfiles);
    }


    public AuthorProfileOutputDto getAuthorProfileByUsername(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        AuthorProfile authorProfile = authorProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No author profile found for user with username " + username));
            return AuthorProfileMapper.authorProfileFromModelToOutputDto(authorProfile);
    }

    public AuthorProfileOutputDto updateAuthorProfile(String username, AuthorProfileOutputDto updatedProfile) {
        AuthorProfile updateProfile = authorProfileRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("No author profile found for username " + username));

        updateProfile.setFirstname(updatedProfile.getFirstname());
        updateProfile.setLastname(updatedProfile.getLastname());
        updateProfile.setBio(updatedProfile.getBio());
        updateProfile.setDob(updatedProfile.getDob());

        AuthorProfile returnAuthorProfile = authorProfileRepository.save(updateProfile);
        return AuthorProfileMapper.authorProfileFromModelToOutputDto(returnAuthorProfile);
    }


    public void deleteAuthorProfile(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        AuthorProfile authorProfile = user.getAuthorProfile();

        if(authorProfile != null && !authorProfile.getStories().isEmpty()) {
            throw new AuthorProfileDeletionException("Cannot delete profile. Author has existing stories.");
        }
        authorProfileRepository.deleteById(username);
        user.setAuthorProfile(null);
        userRepository.save(user);
    }

    @Transactional
    public Resource getPhotoForAuthorProfile(String username) {

        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        AuthorProfile authorProfile = authorProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No author profile found for user with username " + username));

        ProfilePhoto photo = authorProfile.getProfilePhoto();
        if(photo == null) {
            throw new ResourceNotFoundException("Author " + username + " has no photo.");
        }
        return photoService.downLoadFile(photo.getFileName());
    }

    //        return ProfilePhotoMapper.profilePhotoFromModelToOutputDto(photo);


//        Optional<AuthorProfile> optionalAuthorProfile = authorProfileRepository.findById(username);
//        if(optionalAuthorProfile.isEmpty()) {
//            throw new ResourceNotFoundException("No authorprofile found for author with username " + username);
//        }
//        ProfilePhoto photo = optionalAuthorProfile.get().getAuthorProfilePhoto();
//        if(photo == null) {
//            throw new ResourceNotFoundException("Author with username " + username + " has no photo");
//        }
//        return photoService.downLoadFile(photo.getFileName());


    @Transactional
    public AuthorProfile assignPhotoToAuthorProfile(String fileName, String username) {

        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        AuthorProfile authorProfile = authorProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No author profile found for user with username " + username));
        ProfilePhoto photo = fileUploadRepository.findByFileName(fileName)
                .orElseThrow(() -> new ResourceNotFoundException("No author photo found."));

        authorProfile.setProfilePhoto(photo);
        return authorProfileRepository.save(authorProfile);
        }


//
//        Optional<AuthorProfile> optionalAuthorProfile = authorProfileRepository.findById(username);
//        Optional<ProfilePhoto> optionalAuthorProfilePhoto = fileUploadRepository.findByFileName(filename);
//
//        if(optionalAuthorProfile.isPresent() && optionalAuthorProfilePhoto.isPresent()) {
//            ProfilePhoto photo = optionalAuthorProfilePhoto.get();
//            AuthorProfile authorProfile = optionalAuthorProfile.get();
//            authorProfile.setProfilePhoto(photo);
//            return authorProfileRepository.save(authorProfile);
//        } else {
//            throw new ResourceNotFoundException("Author or photo not found");
//        }
    }


