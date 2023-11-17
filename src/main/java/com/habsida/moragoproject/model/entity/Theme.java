package com.habsida.moragoproject.model.entity;

import com.habsida.moragoproject.model.enums.ETheme;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "themes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Theme extends AbstractAuditable{

    @Enumerated(EnumType.STRING)
    private ETheme name;

    private String description;
    private Boolean isActive;
    private Boolean isPopular;
    private String koreanTitle;
    private Double nightPrice;
    private Double price;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;
}

