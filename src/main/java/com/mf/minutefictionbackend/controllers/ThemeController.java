package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.ThemeInputDto;
import com.mf.minutefictionbackend.dtos.mappers.ThemeMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.ThemeOutputDto;
import com.mf.minutefictionbackend.models.Theme;
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


    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;

    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PostMapping
    public ResponseEntity<ThemeOutputDto> createTheme(@Valid @RequestBody ThemeInputDto themeInputDto) {
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

    @GetMapping("/open")
    public ResponseEntity<List<ThemeOutputDto>> getOpenThemes() {
        List<Theme> openThemes = themeService.findOpenThemes();
        List<ThemeOutputDto> openThemeDtoList = ThemeMapper.themeModelListToOutputList(openThemes);
        return ResponseEntity.ok().body(openThemeDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeOutputDto> getThemeById(@PathVariable("id") Long id) {
        ThemeOutputDto themeDto = themeService.getThemeById(id);
        return ResponseEntity.ok().body(themeDto);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<ThemeOutputDto> updateTheme(@Valid @PathVariable("id") Long id, @RequestBody ThemeInputDto updatedThemeInputDto) {
        ThemeOutputDto updatedTheme = themeService.updateTheme(id, updatedThemeInputDto);
        return ResponseEntity.ok().body(updatedTheme);
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThemeById(@PathVariable("id") Long id) {
        themeService.deleteThemeById(id);
        return ResponseEntity.noContent().build();
    }




}
