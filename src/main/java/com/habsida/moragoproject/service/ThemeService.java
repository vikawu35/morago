package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Theme;
import com.habsida.moragoproject.model.enums.ETheme;
import com.habsida.moragoproject.model.input.ThemeInput;
import com.habsida.moragoproject.repository.ThemeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final CategoryService categoryService;
    private final FileService fileService;

    public ThemeService(ThemeRepository themeRepository, CategoryService categoryService, FileService fileService) {
        this.themeRepository = themeRepository;
        this.categoryService = categoryService;
        this.fileService = fileService;
    }

    public List<Theme> findAll(){
        return themeRepository.findAll();
    }

    public Page<Theme> findAllPaged(PageRequest pageRequest) {
        return themeRepository.findAll(pageRequest);
    }

    public Theme findById(Long id){
        return themeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot find Theme by Id: " + id));
    }

    public Theme findByName (ETheme eTheme) {
        return themeRepository.findByName(eTheme)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find Theme by name: " + eTheme));
    }

    public Theme create(Theme theme){

        theme.setDescription("");
        theme.setKoreanTitle("");
        theme.setIsActive(true);
        theme.setIsPopular(false);
        theme.setNightPrice(0d);
        theme.setPrice(0d);

        if (theme.getName() != null) {
            Optional<Theme> existingTheme = themeRepository.findByName(theme.getName());

            if (existingTheme.isPresent()) {
                return existingTheme.get();
            } else {
                themeRepository.save(theme);
            }
        }
        return theme;
    }


    public Boolean delete(Long id){
        themeRepository.deleteById(id);
        return true;
    }

    public Theme update(ETheme name, ThemeInput themeInput){
        Theme theme = findByName(name);

        if (themeInput.getDescription() != null && !themeInput.getDescription().isEmpty()) {
            theme.setDescription(themeInput.getDescription());
        }

        if (themeInput.getKoreanTitle() != null && !themeInput.getKoreanTitle().isEmpty()) {
            theme.setKoreanTitle(themeInput.getKoreanTitle());
        }

        if (themeInput.getIsActive() != null) {
            theme.setIsActive(themeInput.getIsActive());
        }

        if (themeInput.getIsPopular() != null) {
            theme.setIsPopular(themeInput.getIsPopular());
        }

        if (themeInput.getNightPrice() != null) {
            theme.setNightPrice(themeInput.getNightPrice());
        }

        if (themeInput.getPrice() != null) {
            theme.setPrice(themeInput.getPrice());
        }

        if (themeInput.getCategory() != null) {
            theme.setCategory(categoryService.findById(themeInput.getCategory()));
        }

        if (themeInput.getFile() != null) {
            theme.setFile(fileService.findById(themeInput.getFile()));
        }

        return themeRepository.save(theme);
    }
}
