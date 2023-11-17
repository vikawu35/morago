package com.habsida.moragoproject.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rating extends AbstractAuditable{

    private Double grade;

    @OneToOne
    private User userGivesRating;

    @OneToOne
    private User userTakesRating;

    @ManyToOne
    private User user;

}
