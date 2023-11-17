package com.habsida.moragoproject.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrequentlyAskedQuestionInput {

    private String category;
    private String answer;
    private String question;
}
