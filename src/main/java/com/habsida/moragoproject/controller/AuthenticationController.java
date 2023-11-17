package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.input.AuthenticationInput;
import com.habsida.moragoproject.model.input.RefreshTokenInput;
import com.habsida.moragoproject.model.input.TranslatorRegistrationInput;
import com.habsida.moragoproject.model.payload.AuthorizationPayload;
import com.habsida.moragoproject.service.AuthenticationService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;


@Controller
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @MutationMapping
    public AuthorizationPayload registerUser(@Valid @Argument AuthenticationInput authenticationInput) {
        return authenticationService.registerUser(authenticationInput);
    }

    @MutationMapping
    public AuthorizationPayload registerTranslator(@Valid @Argument TranslatorRegistrationInput translatorRegistrationInput) {
        return authenticationService.registerTranslator(translatorRegistrationInput);
    }

    @MutationMapping
    public AuthorizationPayload registerAdmin(@Valid @Argument AuthenticationInput authenticationInput) {
        return authenticationService.registerAdmin(authenticationInput);
    }

    @MutationMapping
    public AuthorizationPayload login(@Valid @Argument AuthenticationInput authenticationInput) {
        return authenticationService.login(authenticationInput);
    }

    @MutationMapping
    public AuthorizationPayload refreshToken(@Argument RefreshTokenInput refreshTokenInput) {
        return authenticationService.refreshToken(refreshTokenInput);
    }
}
