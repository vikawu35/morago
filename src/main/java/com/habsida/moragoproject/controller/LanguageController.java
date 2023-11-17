package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Language;
import com.habsida.moragoproject.model.input.LanguageInput;
import com.habsida.moragoproject.service.LanguageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ADMIN')")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @QueryMapping
    public List<Language> getAllLanguage(){
        return languageService.findAll();
    }

    @QueryMapping
    public Page<Language> getAllLanguagePaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return languageService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Language getLanguageById(@Argument Long id){
        return languageService.findById(id);
    }


    @MutationMapping
    public Boolean deleteLanguageById(@Argument Long id){
        return languageService.delete(id);
    }

    @MutationMapping
    public Language updateLanguage(@Argument Long id, @Argument LanguageInput languageInput){
        return languageService.update(id, languageInput);
    }
}
