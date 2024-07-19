package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.mappers.AuthorProfileMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.exceptions.UsernameNotFoundException;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mf.minutefictionbackend.dtos.mappers.AuthorProfileMapper.*;


@Service
public class AuthorProfileService {

    private final AuthorProfileRepository authorProfileRepository;

    public AuthorProfileService(AuthorProfileRepository authorProfileRepository) {
        this.authorProfileRepository = authorProfileRepository;
    }

    public AuthorProfileOutputDto createAuthorProfile(AuthorProfileInputDto authorProfileInputDto) {
        AuthorProfile authorProfile = authorProfileRepository.save(authorProfileFromInputDtoToModel(authorProfileInputDto));
        return authorProfileFromModelToOutputDto(authorProfile);
    }

    public List<AuthorProfileOutputDto> getAllAuthorProfiles() {
        List<AuthorProfile> allAuthorProfiles = authorProfileRepository.findAll();
        return AuthorProfileMapper.authorProfileModelListToOutputList(allAuthorProfiles);
    }


    public AuthorProfileOutputDto getAuthorProfileByUsername(String username) {
        Optional<AuthorProfile> optionalAuthor = authorProfileRepository.findById(username);
        if(optionalAuthor.isPresent()) {
            return AuthorProfileMapper.authorProfileFromModelToOutputDto(optionalAuthor.get());
        } else {
            throw new UsernameNotFoundException("No user found with username " + username);
        }
    }

    public void updateAuthorProfile(String username, AuthorProfileOutputDto authorDto) {
        Optional<AuthorProfile> profile = authorProfileRepository.findById(username);
        if(profile.isPresent()) {
            AuthorProfile updateProfile = profile.get();
            updateProfile.setUsername(authorDto.getUsername());
            updateProfile.setFirstname(authorDto.getFirstname());
            updateProfile.setLastname(authorDto.getLastname());
            updateProfile.setBio(authorDto.getBio());
            updateProfile.setDob(authorDto.getDob());

            AuthorProfile returnAuthorProfile = authorProfileRepository.save(updateProfile);
            AuthorProfileMapper.authorProfileFromModelToOutputDto(returnAuthorProfile);
        } else {
            throw new UsernameNotFoundException("No user found with username " + username);
        }
    }
}
