package com.habsida.moragoproject.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfile extends AbstractAuditable{

    private Boolean isFreeCallMade;

}
