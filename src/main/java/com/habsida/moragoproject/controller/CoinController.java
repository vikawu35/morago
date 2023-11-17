package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Coin;
import com.habsida.moragoproject.model.input.CoinInput;
import com.habsida.moragoproject.service.CoinService;
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
public class CoinController {

    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @QueryMapping
    public List<Coin> getAllCoin(){
        return coinService.findAll();
    }

    @QueryMapping
    public Page<Coin> getAllCoinPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return coinService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Coin getCoinById(Long id){
        return coinService.findById(id);
    }

    @MutationMapping
    public Coin createCoin(@Argument CoinInput coinInput){
        return coinService.create(coinInput);
    }

    @MutationMapping
    public Boolean deleteCoinById(@Argument Long id){
        return coinService.delete(id);
    }

    @MutationMapping
    public Coin updateCoin(@Argument Long id, @Argument CoinInput coinInput){
        return coinService.update(id, coinInput);
    }
}
