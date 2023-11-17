package com.habsida.moragoproject.model.input;

import com.habsida.moragoproject.model.entity.Language;
import com.habsida.moragoproject.model.entity.Theme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranslatorProfileInput {

    private String dateOfBirth;
    private String email;
    private String levelOfKorean;
    private List<Language> languages;
    private List<Theme> themes;
}
