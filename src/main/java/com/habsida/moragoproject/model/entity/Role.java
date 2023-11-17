package com.habsida.moragoproject.model.entity;

import com.habsida.moragoproject.model.enums.ERole;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role extends AbstractAuditable {

    @Enumerated(EnumType.STRING)
    private ERole name;

}
