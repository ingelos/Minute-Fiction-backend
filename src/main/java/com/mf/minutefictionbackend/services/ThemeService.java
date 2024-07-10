package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.ThemeInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.ThemeOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Theme;
import com.mf.minutefictionbackend.repositories.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mf.minutefictionbackend.dtos.mappers.ThemeMapper.*;

@Service
public class ThemeService {


    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }


    public ThemeOutputDto createTheme(ThemeInputDto themeInputDto) {
        Theme theme = themeRepository.save(themeFromInputDtoToModel(themeInputDto));
        return themeFromModelToOutputDto(theme);
    }


    public List<ThemeOutputDto> getAllThemes() {
        List<Theme> allThemes = themeRepository.findAll();
        return themeModelListToOutputList(allThemes);
    }

    public ThemeOutputDto getThemeById(Long id) {
        Optional<Theme> optionalTheme = themeRepository.findById(id);
        if(optionalTheme.isPresent()) {
            return themeFromModelToOutputDto(optionalTheme.get());
        } else throw new ResourceNotFoundException("No theme found with id " + id);
    }

    public ThemeOutputDto deleteTheme(Long id) {
        Optional<Theme> optionalTheme = themeRepository.findById(id);
        if(optionalTheme.isPresent()) {
            Theme theme = optionalTheme.get();
            themeRepository.delete(theme);
            return themeFromModelToOutputDto(theme);
        } else throw new ResourceNotFoundException("No theme found with id " + id);
    }
}
