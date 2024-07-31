package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.mappers.AuthorProfileMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.exceptions.UsernameNotFoundException;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class AuthorProfileService {

    private final AuthorProfileRepository authorProfileRepository;
    private final UserRepository userRepository;

    public AuthorProfileService(AuthorProfileRepository authorProfileRepository, UserRepository userRepository) {
        this.authorProfileRepository = authorProfileRepository;
        this.userRepository = userRepository;
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


}
