package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.ThemeInputDto;
import com.mf.minutefictionbackend.dtos.mappers.ThemeMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.ThemeOutputDto;
import com.mf.minutefictionbackend.exceptions.BadRequestException;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.Theme;
import com.mf.minutefictionbackend.repositories.ThemeRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
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


    @Transactional
    public ThemeOutputDto createTheme(ThemeInputDto themeInputDto) {
        Theme theme = themeRepository.save(themeFromInputDtoToModel(themeInputDto));
        return ThemeMapper.themeFromModelToOutputDto(theme);
    }

    public List<ThemeOutputDto> getAllThemes() {
        List<Theme> allThemes = themeRepository.findAll();
        return ThemeMapper.themeModelListToOutputList(allThemes);
    }

    public ThemeOutputDto getThemeById(Long themeId) {
        Theme theme = themeRepository.findById(themeId)
                        .orElseThrow(() -> new ResourceNotFoundException("No theme found with id " + themeId));
        return ThemeMapper.themeFromModelToOutputDto(theme);
    }

    @Transactional
    public void deleteThemeById(Long themeId) {
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("No theme found with id " + themeId));
        List<Story> stories = theme.getStories();
        if(stories != null && !stories.isEmpty()) {
            throw new BadRequestException("Selected theme has stories added to it. Delete these stories first.");
        }
        themeRepository.delete(theme);
    }

    @Transactional
    public ThemeOutputDto updateTheme(Long themeId, ThemeInputDto updatedThemeInputDto) {
        Theme updateTheme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("No theme found"));

        updateTheme.setName(updatedThemeInputDto.getName());
        updateTheme.setDescription(updatedThemeInputDto.getDescription());
        updateTheme.setOpenDate(updatedThemeInputDto.getOpenDate());
        updateTheme.setClosingDate(updatedThemeInputDto.getClosingDate());

        Theme returnTheme = themeRepository.save(updateTheme);
        return ThemeMapper.themeFromModelToOutputDto(returnTheme);
    }


    public List<Theme> findOpenThemes() {
        LocalDate now = LocalDate.now();
        return themeRepository.findByClosingDateAfter(now, Sort.by(Sort.Order.asc("closingDate")));
    }

    public List<Theme> findClosedThemes() {
        LocalDate now = LocalDate.now();
        return themeRepository.findByClosingDateBefore(now);
    }
}
