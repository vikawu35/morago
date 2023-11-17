package com.habsida.moragoproject.model.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "translator_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@NamedEntityGraph(
        name = "translator-profile-languages-themes-graph",
        attributeNodes = {
                @NamedAttributeNode("languages"),
                @NamedAttributeNode("themes")
        }
)
public class TranslatorProfile extends AbstractAuditable{

    private String dateOfBirth;
    private String email;
    private Boolean isAvailable;
    private Boolean isOnline;
    private String levelOfKorean;
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "translator_languages",
            joinColumns = @JoinColumn(name = "translator_profile_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "languages_id", referencedColumnName = "id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Language> languages = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "translator_themes",
            joinColumns = @JoinColumn(name = "translator_profile_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "themes_id", referencedColumnName = "id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Theme> themes = new ArrayList<>();
}
