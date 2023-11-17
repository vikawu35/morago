package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Theme;
import com.habsida.moragoproject.model.enums.ETheme;
import com.habsida.moragoproject.model.input.ThemeInput;
import com.habsida.moragoproject.service.ThemeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @QueryMapping
    public List<Theme> getAllTheme(){
        return themeService.findAll();
    }

    @QueryMapping
    public Page<Theme> getAlThemePaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return themeService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Theme getThemeById(@Argument Long id){
        return themeService.findById(id);
    }

    @MutationMapping
    public Boolean deleteThemeById(@Argument Long id){
        return themeService.delete(id);
    }

    @MutationMapping
    public Theme updateTheme(@Argument ETheme name, @Argument ThemeInput themeInput){
        return themeService.update(name, themeInput);
    }
}
