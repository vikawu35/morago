package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.UserProfile;
import com.habsida.moragoproject.model.input.UserProfileInput;
import com.habsida.moragoproject.service.UserProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @QueryMapping
    public List<UserProfile> getAllUserProfile(){
        return userProfileService.findAll();
    }

    @QueryMapping
    public Page<UserProfile> getAllUserProfilePaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userProfileService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public UserProfile getUserProfileById(@Argument Long id){
        return userProfileService.findById(id);
    }


    @MutationMapping
    public UserProfile updateUserProfile(@Argument Long id, @Argument UserProfileInput userProfileInput){
        return userProfileService.update(id, userProfileInput);
    }
}
