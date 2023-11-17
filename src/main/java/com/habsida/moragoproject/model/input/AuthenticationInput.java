package com.habsida.moragoproject.model.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@ToString
@Getter
@Setter
public class AuthenticationInput {

    @Pattern(regexp = "[^a-zA-Z]+", message = "Invalid phone number input")
    private String phone;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
