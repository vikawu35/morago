package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Language;
import com.habsida.moragoproject.model.enums.ELanguage;
import com.habsida.moragoproject.model.input.LanguageInput;
import com.habsida.moragoproject.repository.LanguagesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LanguageService {

    private final LanguagesRepository languagesRepository;

    public LanguageService(LanguagesRepository languagesRepository) {
        this.languagesRepository = languagesRepository;
    }

    public List<Language> findAll(){
        return languagesRepository.findAll();
    }

    public Page<Language> findAllPaged(PageRequest pageRequest) {
        return languagesRepository.findAll(pageRequest);
    }

    public Language findById(Long id){
        return languagesRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find Language by Id: " + id));
    }

    public Language create(Language language){
        if (language.getName() != null) {
            Optional<Language> existingLanguage = languagesRepository.findByName(language.getName());

            if (existingLanguage.isPresent()) {
                return existingLanguage.get();
            } else {
                languagesRepository.save(language);
            }
        }
        return language;
    }

    public Boolean delete(Long id){
        languagesRepository.deleteById(id);
        return true;
    }

    public Language update(Long id, LanguageInput languageInput){
        Language language = findById(id);

        if (languageInput.getName() != null && !languageInput.getName().isEmpty()){
            language.setName(ELanguage.valueOf(languageInput.getName()));
        }

        return languagesRepository.save(language);
    }
}
