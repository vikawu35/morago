package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Language;
import com.habsida.moragoproject.model.entity.Theme;
import com.habsida.moragoproject.model.entity.TranslatorProfile;
import com.habsida.moragoproject.model.input.TranslatorProfileInput;
import com.habsida.moragoproject.repository.TranslatorProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TranslatorProfileService {

    private final TranslatorProfileRepository translatorProfileRepository;

    private final LanguageService languageService;
    private final ThemeService themeService;
    public TranslatorProfileService(TranslatorProfileRepository translatorProfileRepository, LanguageService languageService, ThemeService themeService) {
        this.translatorProfileRepository = translatorProfileRepository;
        this.languageService = languageService;
        this.themeService = themeService;
    }

    public List<TranslatorProfile> findAll(){
        return translatorProfileRepository.findAll();
    }

    public Page<TranslatorProfile> findAllPaged(PageRequest pageRequest) {
        return translatorProfileRepository.findAll(pageRequest);
    }

    public TranslatorProfile findById(Long id){
        return translatorProfileRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find TranslatorProfile by Id: " + id));
    }

    public TranslatorProfile create(TranslatorProfileInput translatorProfileInput){
        TranslatorProfile translatorProfile = new TranslatorProfile();

        translatorProfile.setIsActive(false);
        translatorProfile.setIsAvailable(false);
        translatorProfile.setIsOnline(false);

        if (translatorProfileInput.getDateOfBirth() == null) {
            translatorProfile.setDateOfBirth("");
        } else {
            translatorProfile.setDateOfBirth(translatorProfileInput.getDateOfBirth());
        }
        if (translatorProfileInput.getEmail() == null) {
            translatorProfile.setEmail("");
        } else {
            translatorProfile.setEmail(translatorProfileInput.getEmail());
        }
        if (translatorProfileInput.getLevelOfKorean() == null) {
            translatorProfile.setLevelOfKorean("");
        } else {
            translatorProfile.setLevelOfKorean(translatorProfileInput.getLevelOfKorean());
        }

        if (translatorProfileInput.getLanguages() != null) {
            List<Language> languages = new ArrayList<>();
            for (Language language : translatorProfileInput.getLanguages()) {
                Language createdLanguage = languageService.create(language);
                languages.add(createdLanguage);
            }
            translatorProfile.setLanguages(languages);
        }

        if (translatorProfileInput.getThemes() != null) {
            List<Theme> themes = new ArrayList<>();
            for (Theme theme : translatorProfileInput.getThemes()) {
                Theme createdTheme = themeService.create(theme);
                themes.add(createdTheme);
            }
            translatorProfile.setThemes(themes);
        }
        return translatorProfileRepository.save(translatorProfile);
    }

    public Boolean delete(Long id){
        translatorProfileRepository.deleteById(id);
        return true;
    }

    public TranslatorProfile update(Long id, TranslatorProfileInput translatorProfileInput){
        TranslatorProfile translatorProfile = findById(id);

        if (translatorProfileInput.getDateOfBirth() != null && !translatorProfileInput.getDateOfBirth().isEmpty()) {
            translatorProfile.setDateOfBirth(translatorProfileInput.getDateOfBirth());
        }

        if (translatorProfileInput.getEmail() != null && !translatorProfileInput.getEmail().isEmpty()) {
            translatorProfile.setEmail(translatorProfileInput.getEmail());
        }

        if (translatorProfileInput.getLevelOfKorean() != null && !translatorProfileInput.getLevelOfKorean().isEmpty()) {
            translatorProfile.setLevelOfKorean(translatorProfileInput.getLevelOfKorean());
        }

        if (translatorProfileInput.getLanguages() != null) {
            translatorProfile.setLanguages(translatorProfileInput.getLanguages());
        }

        return translatorProfileRepository.save(translatorProfile);
    }
}
