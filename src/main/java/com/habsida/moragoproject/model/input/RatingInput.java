package com.habsida.moragoproject.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingInput {

    private Double grade;

    private Long userGivesRating;
    private Long userTakesRating;
    private Long user;
}
