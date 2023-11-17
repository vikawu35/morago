package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.PasswordReset;
import com.habsida.moragoproject.model.input.PasswordResetInput;
import com.habsida.moragoproject.model.payload.PasswordResetPayload;
import com.habsida.moragoproject.service.PasswordResetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @QueryMapping
    public List<PasswordReset> getAllPasswordReset(){
        return passwordResetService.findAll();
    }

    @QueryMapping
    public Page<PasswordReset> getAllPasswordResetPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return passwordResetService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public PasswordReset getPasswordResetById(@Argument Long id){
        return passwordResetService.findById(id);
    }

    @MutationMapping
    public Boolean deletePasswordResetById(@Argument Long id){
        return passwordResetService.delete(id);
    }

    @MutationMapping
    public PasswordResetPayload requestPasswordReset(@Argument String phone) {
        return passwordResetService.requestPasswordReset(phone);
    }

    @MutationMapping
    public String validateVerificationCode(@Argument PasswordResetInput passwordResetInput) {
        return passwordResetService.validateVerificationCode(passwordResetInput);
    }
}
