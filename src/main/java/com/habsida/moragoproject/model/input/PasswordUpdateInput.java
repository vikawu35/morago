package com.habsida.moragoproject.model.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PasswordUpdateInput {

    private String token;

    @NotBlank(message = "Password cannot be blank")
    private String newPassword;
}
