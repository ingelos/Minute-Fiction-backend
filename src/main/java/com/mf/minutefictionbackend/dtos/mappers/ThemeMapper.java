package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.ThemeInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.ThemeOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Theme;

import java.util.ArrayList;
import java.util.List;

public class ThemeMapper {

    public static Theme themeFromInputDtoToModel(ThemeInputDto themeInputDto) {
        Theme theme = new Theme();
        theme.setName(themeInputDto.getName());
        theme.setDescription(themeInputDto.getDescription());
        theme.setOpenDate(themeInputDto.getOpenDate());
        theme.setClosingDate(themeInputDto.getClosingDate());

        return theme;
    }

    public static ThemeOutputDto themeFromModelToOutputDto(Theme theme) {
        ThemeOutputDto themeOutputDto = new ThemeOutputDto();
        themeOutputDto.setId(theme.getId());
        themeOutputDto.setName(theme.getName());
        themeOutputDto.setDescription(theme.getDescription());
        themeOutputDto.setOpenDate(theme.getOpenDate());
        themeOutputDto.setClosingDate(theme.getClosingDate());

        return themeOutputDto;
    }

    public static List<ThemeOutputDto> themeModelListToOutputList(List<Theme> themes) {
        if(themes.isEmpty()) {
            throw new ResourceNotFoundException("No themes found.");
        }
        List<ThemeOutputDto> themeOutputDtoList = new ArrayList<>();
        themes.forEach((theme) -> themeOutputDtoList.add(themeFromModelToOutputDto(theme)));
        return themeOutputDtoList;
    }

}
