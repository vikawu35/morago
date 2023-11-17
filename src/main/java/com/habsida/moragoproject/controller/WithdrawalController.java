package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Withdrawal;
import com.habsida.moragoproject.model.input.WithdrawalInput;
import com.habsida.moragoproject.service.WithdrawalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    public WithdrawalController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @QueryMapping
    public List<Withdrawal> getAllWithdrawalService(){
        return withdrawalService.findAll();
    }

    @QueryMapping
    public Page<Withdrawal> getAllWithdrawalPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return withdrawalService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Withdrawal getWithdrawalById(@Argument Long id){
        return withdrawalService.findById(id);
    }

    @MutationMapping
    public Withdrawal createWithdrawal(@Argument WithdrawalInput withdrawalInput){
        return withdrawalService.create(withdrawalInput);
    }

    @MutationMapping
    public Boolean deleteWithdrawalById(@Argument Long id){
        return withdrawalService.delete(id);
    }

    @MutationMapping
    public Withdrawal updateWithdrawal(@Argument Long id, @Argument WithdrawalInput withdrawalInput){
        return withdrawalService.update(id, withdrawalInput);
    }
}
