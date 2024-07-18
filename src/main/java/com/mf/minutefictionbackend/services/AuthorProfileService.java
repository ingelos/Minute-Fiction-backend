package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.AuthorProfileInputDto;
import com.mf.minutefictionbackend.dtos.mappers.AuthorProfileMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.AuthorProfileOutputDto;
import com.mf.minutefictionbackend.models.AuthorProfile;
import com.mf.minutefictionbackend.repositories.AuthorProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mf.minutefictionbackend.dtos.mappers.AuthorProfileMapper.authorProfileFromInputDtoToModel;
import static com.mf.minutefictionbackend.dtos.mappers.AuthorProfileMapper.authorProfileFromModelToOutputDto;



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
}
