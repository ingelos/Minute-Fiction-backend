package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.ThemeInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.ThemeOutputDto;
import com.mf.minutefictionbackend.services.ThemeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<ThemeOutputDto> createTheme(@Valid @RequestBody ThemeInputDto themeInputDto) {
        ThemeOutputDto theme = themeService.createTheme(themeInputDto);
        // add authority editor

        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + theme.name).toUriString());

        return ResponseEntity.created(uri).body(theme);
    }

    @GetMapping
    public ResponseEntity<List<ThemeOutputDto>> getAllThemes() {
        return ResponseEntity.ok().body(themeService.getAllThemes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThemeOutputDto> getThemeById(@PathVariable("id") Long id) {
        ThemeOutputDto optionalTheme = themeService.getThemeById(id);
        return ResponseEntity.ok().body(optionalTheme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable("id") Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.noContent().build();
    }



}
