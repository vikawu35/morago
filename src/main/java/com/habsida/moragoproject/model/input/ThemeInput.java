package com.habsida.moragoproject.model.input;

import com.habsida.moragoproject.model.enums.ETheme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeInput {

    private String description;
    private String koreanTitle;
    private ETheme name;
    private Boolean isActive;
    private Boolean isPopular;
    private Double nightPrice;
    private Double price;
    private Long category;
    private Long file;
}
