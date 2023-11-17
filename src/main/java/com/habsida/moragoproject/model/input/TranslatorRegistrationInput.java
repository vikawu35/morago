package com.habsida.moragoproject.model.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@ToString
public class TranslatorRegistrationInput {

    @Pattern(regexp = "[^a-zA-Z]+", message = "Invalid phone number input")
    private String phone;

    @NotBlank(message = "Password cannot be blank")
    private String password;
    private String firstName;
    private String lastName;
    private TranslatorProfileInput translatorProfile;
}
