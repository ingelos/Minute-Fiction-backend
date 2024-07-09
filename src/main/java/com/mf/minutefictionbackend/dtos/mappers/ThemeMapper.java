package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.ThemeInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.ThemeOutputDto;
import com.mf.minutefictionbackend.models.Theme;

import java.util.ArrayList;
import java.util.List;

public class ThemeMapper {

    public static Theme themeFromInputDtoToModel(ThemeInputDto themeInputDto) {
        Theme theme = new Theme();
        theme.setName(themeInputDto.getName());
        theme.setOpenDate(themeInputDto.getOpenDate());
        theme.setClosingDate(themeInputDto.getClosingDate());

        return theme;
    }

    public static ThemeOutputDto themeFromModelToOutputDto(Theme theme) {
        ThemeOutputDto themeOutputDto = new ThemeOutputDto();
        themeOutputDto.setId(theme.getId());
        themeOutputDto.setName(theme.getName());
        themeOutputDto.setOpenDate(theme.getOpenDate());
        themeOutputDto.setClosingDate(theme.getClosingDate());

        return themeOutputDto;
    }

    public static List<ThemeOutputDto> themeModelListToOutputList(List<Theme> themes) {
        List<ThemeOutputDto> themeOutputDtoList = new ArrayList<>();

        themes.forEach((theme) -> themeOutputDtoList.add(themeFromModelToOutputDto(theme)));
        return themeOutputDtoList;
    }

}
