package com.habsida.moragoproject.model.entity;

import com.habsida.moragoproject.model.enums.ELanguage;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "languages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Language extends AbstractAuditable{

    @Enumerated(EnumType.STRING)
    private ELanguage name;
}
