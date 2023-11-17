package com.habsida.moragoproject.service;


import com.habsida.moragoproject.exception.InvalidInputException;
import com.habsida.moragoproject.exception.JwtTokenException;
import com.habsida.moragoproject.exception.ResourceAlreadyExistException;
import com.habsida.moragoproject.model.entity.*;
import com.habsida.moragoproject.model.enums.ERole;
import com.habsida.moragoproject.model.input.AuthenticationInput;
import com.habsida.moragoproject.model.input.RefreshTokenInput;
import com.habsida.moragoproject.model.input.TranslatorRegistrationInput;
import com.habsida.moragoproject.model.input.UserInput;
import com.habsida.moragoproject.model.payload.AuthorizationPayload;
import com.habsida.moragoproject.security.JwtGenerator;
import com.habsida.moragoproject.security.RefreshTokenGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtGenerator jwtGenerator;
    private final RefreshTokenGenerator refreshTokenGenerator;
    private final TranslatorProfileService translatorProfileService;
    private final UserProfileService userProfileService;


    public AuthenticationService(AuthenticationManager authenticationManager, UserService userService, JwtGenerator jwtGenerator, RefreshTokenGenerator refreshTokenGenerator, TranslatorProfileService translatorProfileService, UserProfileService userProfileService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
        this.refreshTokenGenerator = refreshTokenGenerator;
        this.translatorProfileService = translatorProfileService;
        this.userProfileService = userProfileService;
    }

    public AuthorizationPayload registerUser(AuthenticationInput authenticationInput) {
        UserInput userInput = new UserInput();

        String userPhone = authenticationInput.getPhone().replaceAll("[^0-9]", "");

        if (userService.existsByPhone(userPhone)) {
            throw new ResourceAlreadyExistException("User with this phone number already exists");
        }

        userInput.setPhone(userPhone);
        userInput.setPassword(authenticationInput.getPassword());
        userInput.setRoles(List.of(new Role(ERole.USER)));

        User newUser = userService.create(userInput);

        UserProfile userProfile = userProfileService.create();

        newUser.setUserProfile(userProfile);

        String jwtToken = jwtGenerator.generateTokenFromUsername(newUser.getPhone());
        RefreshToken refreshToken = refreshTokenGenerator.createRefreshToken(userPhone);

        return new AuthorizationPayload(jwtToken, refreshToken.getToken());
    }

    public AuthorizationPayload registerTranslator(TranslatorRegistrationInput translatorRegistrationInput) {

        UserInput userInput = new UserInput();

        String cleanedPhone = translatorRegistrationInput.getPhone().replaceAll("[^0-9]", "");

        if (userService.existsByPhone(cleanedPhone)) {
            throw new ResourceAlreadyExistException("User with this phone number already exists");
        }

        userInput.setPhone(cleanedPhone);
        userInput.setPassword(translatorRegistrationInput.getPassword());

        userInput.setFirstName(translatorRegistrationInput.getFirstName());
        userInput.setLastName(translatorRegistrationInput.getLastName());
        userInput.setRoles(List.of(new Role(ERole.TRANSLATOR)));

        User newUser = userService.create(userInput);

        TranslatorProfile translatorProfile = translatorProfileService.create(translatorRegistrationInput.getTranslatorProfile());

        newUser.setTranslatorProfile(translatorProfile);

        String jwtToken = jwtGenerator.generateTokenFromUsername(newUser.getPhone());
        RefreshToken refreshToken = refreshTokenGenerator.createRefreshToken(cleanedPhone);

        return new AuthorizationPayload(jwtToken, refreshToken.getToken());
    }

    public AuthorizationPayload registerAdmin(AuthenticationInput authenticationInput) {
        UserInput userInput = new UserInput();

        String cleanedPhone = authenticationInput.getPhone().replaceAll("[^0-9]", "");

        if (userService.existsByPhone(cleanedPhone)) {
            throw new ResourceAlreadyExistException("User with this phone number already exists");
        }

        userInput.setPhone(cleanedPhone);
        userInput.setPassword(authenticationInput.getPassword());
        userInput.setRoles(Arrays.asList(new Role(ERole.ADMIN),
                new Role(ERole.USER),
                new Role(ERole.TRANSLATOR)));

        User newUser = userService.create(userInput);

        String jwtToken = jwtGenerator.generateTokenFromUsername(newUser.getPhone());
        RefreshToken refreshToken = refreshTokenGenerator.createRefreshToken(cleanedPhone);

        return new AuthorizationPayload(jwtToken, refreshToken.getToken());
    }

    public AuthorizationPayload login(AuthenticationInput authenticationInput) {

        String cleanedPhone = authenticationInput.getPhone().replaceAll("[^0-9]", "");

        User user  = userService.findByPhone(cleanedPhone);
        if (user.getIsDeleted()) {
            throw new InvalidInputException("User was deleted");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        cleanedPhone,
                        authenticationInput.getPassword())
                );


        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtGenerator.generateJwtToken(userDetails);
        RefreshToken refreshToken = refreshTokenGenerator.createRefreshToken(cleanedPhone);

        return new AuthorizationPayload(jwtToken, refreshToken.getToken());
    }

    public AuthorizationPayload refreshToken (RefreshTokenInput refreshTokenInput) {
        String refreshToken = refreshTokenInput.getRefreshToken();

        return refreshTokenGenerator.findByToken(refreshToken)
                .map(refreshTokenGenerator::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String jwtToken = jwtGenerator.generateTokenFromUsername(user.getPhone());
                    RefreshToken newRefreshToken = refreshTokenGenerator.createRefreshToken(user.getPhone());
                    return new AuthorizationPayload(jwtToken, newRefreshToken.getToken());
                })
                .orElseThrow(()-> new JwtTokenException("Refresh token is not found"));
    }
}
