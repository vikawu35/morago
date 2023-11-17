package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Coin;
import com.habsida.moragoproject.model.input.CoinInput;
import com.habsida.moragoproject.repository.CoinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CoinService {

    private final CoinRepository coinRepository;

    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    public List<Coin> findAll(){
        return coinRepository.findAll();
    }

    public Page<Coin> findAllPaged(PageRequest pageRequest) {
        return coinRepository.findAll(pageRequest);
    }

    public Coin findById(Long id){
        return coinRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cannot find Coin by Id: " + id));
    }

    public Coin create(CoinInput coinInput){
        Coin coin = new Coin();

        if (coinInput.getCoin() == null) {
            coin.setCoin(0d);
        } else {
            coin.setCoin(coinInput.getCoin());
        }

        if (coinInput.getWon() == null) {
            coin.setWon(0d);
        } else {
            coin.setWon(coinInput.getWon());
        }

        return coinRepository.save(coin);
    }

    public Boolean delete(Long id){
        coinRepository.deleteById(id);
        return true;
    }

    public Coin update(Long id, CoinInput coinInput){
        Coin coin = findById(id);

        if (coinInput.getCoin() != null) {
            coin.setCoin(coinInput.getCoin());
        }

        if (coinInput.getWon() != null) {
            coin.setWon(coinInput.getWon());
        }

        return coinRepository.save(coin);
    }
}
