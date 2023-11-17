package com.habsida.moragoproject.model.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsernameUpdateInput {

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Pattern(regexp = "[^a-zA-Z]+", message = "Invalid phone number input")
    private String newPhone;
}
