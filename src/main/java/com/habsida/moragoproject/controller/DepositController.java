package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Deposit;
import com.habsida.moragoproject.model.input.DepositInput;
import com.habsida.moragoproject.service.DepositService;
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
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @QueryMapping
    public List<Deposit> getAllDeposit(){
        return depositService.findAll();
    }

    @QueryMapping
    public Page<Deposit> getAllDepositPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return depositService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Deposit getDepositById(@Argument Long id){
        return depositService.findById(id);
    }

    @MutationMapping
    public Deposit createDeposit(@Argument DepositInput depositInput){
        return depositService.create(depositInput);
    }

    @MutationMapping
    public Boolean deleteDepositById(@Argument Long id){
        return depositService.delete(id);
    }

    @MutationMapping
    public Deposit updateDeposit(@Argument Long id, @Argument DepositInput depositInput){
        return depositService.update(id, depositInput);
    }
}
