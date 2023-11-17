package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Deposit;
import com.habsida.moragoproject.model.enums.EStatus;
import com.habsida.moragoproject.model.input.DepositInput;
import com.habsida.moragoproject.repository.DepositRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final UserService userService;

    public DepositService(DepositRepository depositRepository, UserService userService) {
        this.depositRepository = depositRepository;
        this.userService = userService;
    }

    public List<Deposit> findAll(){
        return depositRepository.findAll();
    }

    public Page<Deposit> findAllPaged(PageRequest pageRequest) {
        return depositRepository.findAll(pageRequest);
    }

    public Deposit findById(Long id){
        return depositRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find Deposit by Id: " + id));
    }

    public Deposit create(DepositInput depositInput){
        Deposit deposit = new Deposit();

        if (!isNull(depositInput.getAccountHolder()) && !depositInput.getAccountHolder().isEmpty()) {
            deposit.setAccountHolder(depositInput.getAccountHolder());
        } else {
            deposit.setAccountHolder("");
        }
        if (!isNull(depositInput.getNameOfBank()) && !depositInput.getNameOfBank().isEmpty()) {
            deposit.setNameOfBank(depositInput.getNameOfBank());
        } else {
            deposit.setNameOfBank("");
        }
        if (!isNull(depositInput.getCoin())) {
            deposit.setCoin(depositInput.getCoin());
        }else{
            deposit.setCoin(0d);
        }
        if (!isNull(depositInput.getWon())) {
            deposit.setWon(depositInput.getWon());
        } else {
            deposit.setWon(0d);
        }
        if (!isNull(depositInput.getStatus()) && !depositInput.getStatus().isEmpty()) {
            deposit.setStatus(EStatus.valueOf(depositInput.getStatus()));
        } else {
            deposit.setStatus(EStatus.E100);
        }
        if (!isNull(depositInput.getUser())) {
            deposit.setUser(userService.findById(depositInput.getUser()));
        }
        return depositRepository.save(deposit);
    }

    public Boolean delete(Long id){
        depositRepository.deleteById(id);
        return true;
    }

    public Deposit update(Long id, DepositInput depositInput){
        Deposit deposit = findById(id);

        if(depositInput.getAccountHolder() != null && !depositInput.getAccountHolder().isEmpty()) {
            deposit.setAccountHolder(depositInput.getAccountHolder());
        }

        if(depositInput.getNameOfBank() != null && !depositInput.getNameOfBank().isEmpty()) {
            deposit.setNameOfBank(depositInput.getNameOfBank());
        }

        if(depositInput.getCoin() != null) {
            deposit.setCoin(depositInput.getCoin());
        }

        if(depositInput.getWon() != null) {
            deposit.setWon(depositInput.getWon());
        }

        if (depositInput.getStatus() != null) {
            deposit.setStatus(EStatus.valueOf(depositInput.getStatus()));
        }

        return depositRepository.save(deposit);
    }
}
