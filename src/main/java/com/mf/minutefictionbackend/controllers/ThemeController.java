package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.ThemeInputDto;
import com.mf.minutefictionbackend.dtos.mappers.ThemeMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.CommentOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.StoryOutputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.ThemeOutputDto;
import com.mf.minutefictionbackend.models.Story;
import com.mf.minutefictionbackend.models.Theme;
import com.mf.minutefictionbackend.services.SecurityService;
import com.mf.minutefictionbackend.services.ThemeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;
    private final SecurityService securityService;

    public ThemeController(ThemeService themeService, SecurityService securityService) {
        this.themeService = themeService;
        this.securityService = securityService;
    }

    @PostMapping
    public ResponseEntity<ThemeOutputDto> createTheme(@Valid @RequestBody ThemeInputDto themeInputDto) {
        securityService.checkIsEditor();
        ThemeOutputDto theme = themeService.createTheme(themeInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + theme.getName()).toUriString());
        return ResponseEntity.created(uri).body(theme);
    }

    @GetMapping
    public ResponseEntity<List<ThemeOutputDto>> getAllThemes() {
        return ResponseEntity.ok().body(themeService.getAllThemes());
    }

    @GetMapping("/{themeId}")
    public ResponseEntity<ThemeOutputDto> getThemeById(@PathVariable("themeId") Long themeId) {
        ThemeOutputDto themeDto = themeService.getThemeById(themeId);
        return ResponseEntity.ok().body(themeDto);
    }

    @GetMapping("/closed")
    public ResponseEntity<List<ThemeOutputDto>> getClosedThemes() {
        List<Theme> closedThemes = themeService.findClosedThemes();
        List<ThemeOutputDto> closedThemeDtoList = ThemeMapper.themeModelListToOutputList(closedThemes);
        return ResponseEntity.ok().body(closedThemeDtoList);
    }

    @GetMapping("/open")
    public ResponseEntity<List<ThemeOutputDto>> getOpenThemes() {
        List<Theme> openThemes = themeService.findOpenThemes();
        List<ThemeOutputDto> openThemeDtoList = ThemeMapper.themeModelListToOutputList(openThemes);
        return ResponseEntity.ok().body(openThemeDtoList);
    }

    // MANAGE THEMES

    @PutMapping("/{themeId}")
    public ResponseEntity<ThemeOutputDto> updateTheme(@Valid @PathVariable("themeId") Long themeId, @RequestBody ThemeInputDto updatedThemeInputDto) {
        securityService.checkIsEditor();
        ThemeOutputDto updatedTheme = themeService.updateTheme(themeId, updatedThemeInputDto);
        return ResponseEntity.ok().body(updatedTheme);
    }

    @DeleteMapping("/{themeId}")
    public ResponseEntity<Void> deleteThemeById(@PathVariable("themeId") Long themeId) {
        securityService.checkIsEditor();
        themeService.deleteThemeById(themeId);
        return ResponseEntity.noContent().build();
    }




}
