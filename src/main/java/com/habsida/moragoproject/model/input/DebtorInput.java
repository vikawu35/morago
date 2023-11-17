package com.habsida.moragoproject.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebtorInput {

    private String accountHolder;
    private Boolean isPaid;
    private String nameOfBank;

    private Long user;
}
