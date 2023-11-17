package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Call;
import com.habsida.moragoproject.model.input.CallInput;
import com.habsida.moragoproject.service.CallService;
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
public class CallController {

    private final CallService callService;

    public CallController(CallService callService) {
        this.callService = callService;
    }

    @QueryMapping
    public List<Call> getAllCall(){
        return callService.findAll();
    }

    @QueryMapping
    public Page<Call> getAllCallPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return callService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Call getCallById(@Argument Long id){
        return callService.findById(id);
    }

    @MutationMapping
    public Call createCall(@Argument CallInput callInput){
        return callService.create(callInput);
    }

    @MutationMapping
    public Boolean deleteCallById(@Argument Long id){
        return callService.delete(id);
    }

    @MutationMapping
    public Call updateCall(@Argument Long id, @Argument CallInput callInput){
        return callService.update(id, callInput);
    }
}
