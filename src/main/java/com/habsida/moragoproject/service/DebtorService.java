package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Debtor;
import com.habsida.moragoproject.model.input.DebtorInput;
import com.habsida.moragoproject.repository.DebtorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DebtorService {

    private final DebtorRepository debtorRepository;
    private final UserService userService;

    public DebtorService(DebtorRepository debtorRepository, UserService userService) {
        this.debtorRepository = debtorRepository;
        this.userService = userService;
    }

    public List<Debtor> findAll(){
        return debtorRepository.findAll();
    }

    public Page<Debtor> findAllPaged(PageRequest pageRequest) {
        return debtorRepository.findAll(pageRequest);
    }

    public Debtor findById(Long id){
        return debtorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Debtor was not found by Id: " + id));
    }

    public Debtor create(DebtorInput debtorInput){
        Debtor debtor = new Debtor();

        if (debtorInput.getAccountHolder() != null && !debtorInput.getAccountHolder().isEmpty()) {
            debtor.setAccountHolder(debtorInput.getAccountHolder());
        } else {
            debtor.setAccountHolder("");
        }

        if (debtorInput.getNameOfBank() != null && !debtorInput.getNameOfBank().isEmpty()) {
            debtor.setNameOfBank(debtorInput.getNameOfBank());
        } else {
            debtor.setNameOfBank("");
        }

        if (debtorInput.getIsPaid() != null) {
            debtor.setIsPaid(debtorInput.getIsPaid());
        } else {
            debtor.setIsPaid(false);
        }

        if (debtorInput.getUser() != null) {
            debtor.setUser(userService.findById(debtorInput.getUser()));
        }
        return debtorRepository.save(debtor);
    }

    public Boolean delete(Long id){
        debtorRepository.deleteById(id);
        return true;
    }

    public Debtor update(Long id, DebtorInput debtorInput){
        Debtor debtor = findById(id);

        if (debtorInput.getAccountHolder() != null && !debtorInput.getAccountHolder().isEmpty()) {
            debtor.setAccountHolder(debtorInput.getAccountHolder());
        }

        if (debtorInput.getNameOfBank() != null && !debtorInput.getNameOfBank().isEmpty()) {
            debtor.setNameOfBank(debtorInput.getNameOfBank());
        }

        if (debtorInput.getIsPaid() != null) {
            debtor.setIsPaid(debtorInput.getIsPaid());
        }

        return debtorRepository.save(debtor);
    }
}
