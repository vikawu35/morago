package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Withdrawal;
import com.habsida.moragoproject.model.enums.EStatus;
import com.habsida.moragoproject.model.input.WithdrawalInput;
import com.habsida.moragoproject.repository.WithdrawalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;
    private final UserService userService;

    public WithdrawalService(WithdrawalRepository withdrawalRepository, UserService userService) {
        this.withdrawalRepository = withdrawalRepository;
        this.userService = userService;
    }

    public List<Withdrawal> findAll(){
        return withdrawalRepository.findAll();
    }

    public Page<Withdrawal> findAllPaged(PageRequest pageRequest) {
        return withdrawalRepository.findAll(pageRequest);
    }

    public Withdrawal findById(Long id){
        return withdrawalRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cannot find Withdrawal by Id: " + id));
    }

    public Withdrawal create(WithdrawalInput withdrawalInput){
        Withdrawal withdrawal = new Withdrawal();

        if (!isNull(withdrawalInput.getStatus()) && !withdrawalInput.getStatus().isEmpty()){
            withdrawal.setStatus(EStatus.valueOf(withdrawalInput.getStatus()));
        } else{
            withdrawal.setStatus(EStatus.E100);
        }

        if (!isNull(withdrawalInput.getAccountNumber()) && !withdrawalInput.getAccountNumber().isEmpty()){
            withdrawal.setAccountNumber(withdrawalInput.getAccountNumber());
        } else {
            withdrawal.setAccountHolder("");
        }

        if (!isNull(withdrawalInput.getAccountHolder()) && !withdrawalInput.getAccountHolder().isEmpty()){
            withdrawal.setAccountHolder(withdrawalInput.getAccountHolder());
        } else {
            withdrawal.setAccountHolder("");
        }

        if (!isNull(withdrawalInput.getNameOfBank()) && !withdrawalInput.getNameOfBank().isEmpty()){
            withdrawal.setNameOfBank(withdrawalInput.getNameOfBank());
        } else {
            withdrawal.setNameOfBank("");
        }

        if (!isNull(withdrawalInput.getSum())){
            withdrawal.setSum(withdrawalInput.getSum());
        } else {
            withdrawal.setSum(0d);
        }

        if (!isNull(withdrawalInput.getUser())){
            withdrawal.setUser(userService.findById(withdrawalInput.getUser()));
        }
        return withdrawalRepository.save(withdrawal);
    }

    public Boolean delete(Long id) {
        withdrawalRepository.deleteById(id);
        return true;
    }

    public Withdrawal update(Long id, WithdrawalInput withdrawalInput){
        Withdrawal withdrawal = findById(id);

        if (!isNull(withdrawalInput.getStatus()) && !withdrawalInput.getStatus().isEmpty()){
            withdrawal.setStatus(EStatus.valueOf(withdrawalInput.getStatus()));
        }

        if (!isNull(withdrawalInput.getAccountNumber()) && !withdrawalInput.getAccountNumber().isEmpty()){
            withdrawal.setAccountNumber(withdrawalInput.getAccountNumber());
        }

        if ( !isNull(withdrawalInput.getAccountHolder()) && !withdrawalInput.getAccountHolder().isEmpty()){
            withdrawal.setAccountHolder(withdrawalInput.getAccountHolder());
        }

        if (!isNull(withdrawalInput.getNameOfBank()) && !withdrawalInput.getNameOfBank().isEmpty()){
            withdrawal.setNameOfBank(withdrawalInput.getNameOfBank());
        }

        if (!isNull(withdrawalInput.getSum())){
            withdrawal.setSum(withdrawalInput.getSum());
        }

        return withdrawalRepository.save(withdrawal);
    }
}
