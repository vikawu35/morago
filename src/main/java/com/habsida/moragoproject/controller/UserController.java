package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.User;
import com.habsida.moragoproject.model.input.PasswordUpdateInput;
import com.habsida.moragoproject.model.input.UserInput;
import com.habsida.moragoproject.model.input.UsernameUpdateInput;
import com.habsida.moragoproject.model.payload.Profile;
import com.habsida.moragoproject.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<User> getAllUser(){
        return userService.findAll();
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Page<User> getAllUserPaged (@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userService.findAllPaged(pageRequest);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public User getUserById(@Argument Long id){
        return userService.findById(id);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Boolean deleteUserById(@Argument Long id){
        return userService.delete(id);
    }

    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public User updateUser(@Argument Long id, @Argument UserInput userInput ){
        return userService.update(id, userInput);
    }

    @QueryMapping
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @MutationMapping
    public Boolean deleteCurrentUser() {
        return userService.deleteCurrentUser();
    }

    @SchemaMapping(typeName = "User", field = "profile")
    public Profile getProfile(User user) {
        return userService.getProfile(user);
    }

    @MutationMapping
    public User updatePassword(@Valid @Argument PasswordUpdateInput passwordUpdateInput) {
        return userService.updatePassword(passwordUpdateInput);
    }

    @MutationMapping
    public String updateUsername(@Valid @Argument UsernameUpdateInput usernameUpdateInput) {
        return userService.updateUsername(usernameUpdateInput);
    }

}
