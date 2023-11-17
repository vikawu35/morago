package com.habsida.moragoproject.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class File extends AbstractAuditable{

    private String originalTitle;
    private String path;
    private String type;
}
