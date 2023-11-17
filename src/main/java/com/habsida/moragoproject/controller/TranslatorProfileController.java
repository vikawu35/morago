package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.TranslatorProfile;
import com.habsida.moragoproject.model.input.TranslatorProfileInput;
import com.habsida.moragoproject.service.TranslatorProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TranslatorProfileController {

    private final TranslatorProfileService translatorProfileService;

    public TranslatorProfileController(TranslatorProfileService translatorProfileService) {
        this.translatorProfileService = translatorProfileService;
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<TranslatorProfile> getAllTranslatorProfile(){
        return translatorProfileService.findAll();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<TranslatorProfile> getAllTranslatorProfilePaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return translatorProfileService.findAllPaged(pageRequest);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public TranslatorProfile getTranslatorProfileById(@Argument Long id){
        return translatorProfileService.findById(id);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TRANSLATOR')")
    public TranslatorProfile updateTranslatorProfile(@Argument Long id, @Argument TranslatorProfileInput translatorProfileInput){
        return translatorProfileService.update(id, translatorProfileInput);
    }
}