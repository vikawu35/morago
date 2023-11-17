package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Debtor;
import com.habsida.moragoproject.model.input.DebtorInput;
import com.habsida.moragoproject.service.DebtorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ADMIN')")
public class DebtorController {

    private final DebtorService debtorService;

    public DebtorController(DebtorService debtorService) {
        this.debtorService = debtorService;
    }

    @QueryMapping
    public List<Debtor> getAllDebtor(){
        return debtorService.findAll();
    }

    @QueryMapping
    public Page<Debtor> getAllDebtorPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return debtorService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Debtor getDebtorById(@Argument Long id){
        return debtorService.findById(id);
    }

    @MutationMapping
    public Debtor createDebtor(@Argument DebtorInput debtorInput){
        return debtorService.create(debtorInput);
    }

    @MutationMapping
    public Boolean deleteDebtorById(@Argument Long id){
        return debtorService.delete(id);
    }

    @MutationMapping
    public Debtor updateDebtor(@Argument Long id, @Argument DebtorInput debtorInput){
        return debtorService.update(id, debtorInput);
    }
}

