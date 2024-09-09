package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.ThemeInputDto;
import com.mf.minutefictionbackend.dtos.mappers.ThemeMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.ThemeOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Theme;
import com.mf.minutefictionbackend.repositories.ThemeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.mf.minutefictionbackend.dtos.mappers.ThemeMapper.*;

@Service
public class ThemeService {


    private final ThemeRepository themeRepository;


    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }


    public ThemeOutputDto createTheme(ThemeInputDto themeInputDto) {
        Theme theme = themeRepository.save(themeFromInputDtoToModel(themeInputDto));
        return ThemeMapper.themeFromModelToOutputDto(theme);
    }

    public List<ThemeOutputDto> getAllThemes() {
        List<Theme> allThemes = themeRepository.findAll();
        return ThemeMapper.themeModelListToOutputList(allThemes);
    }

    public ThemeOutputDto getThemeById(Long id) {
        Theme theme = themeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No theme found with id " + id));
        return ThemeMapper.themeFromModelToOutputDto(theme);
    }


    public void deleteThemeById(Long themeId) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("No theme found with id " + themeId));
        themeRepository.delete(theme);
    }

    public ThemeOutputDto updateTheme(Long themeId, ThemeInputDto updatedThemeInputDto) {
        Theme updateTheme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("No theme found"));

        if(updatedThemeInputDto.getName() != null) {
            updateTheme.setName(updatedThemeInputDto.getName());
        }
        if(updatedThemeInputDto.getDescription() != null) {
            updateTheme.setDescription(updatedThemeInputDto.getDescription());
        }
        if(updatedThemeInputDto.getOpenDate() != null) {
            updateTheme.setOpenDate(updatedThemeInputDto.getOpenDate());
        }
        if(updatedThemeInputDto.getClosingDate() != null) {
            updateTheme.setClosingDate(updatedThemeInputDto.getClosingDate());
        }

        Theme returnTheme = themeRepository.save(updateTheme);
        return ThemeMapper.themeFromModelToOutputDto(returnTheme);
    }


    public List<Theme> findOpenThemes() {
        LocalDate now = LocalDate.now();

        return themeRepository.findByClosingDateAfter(now);
    }
}
