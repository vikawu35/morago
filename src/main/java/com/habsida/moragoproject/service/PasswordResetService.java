package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.InvalidInputException;
import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.PasswordReset;
import com.habsida.moragoproject.model.entity.User;
import com.habsida.moragoproject.model.input.PasswordResetInput;
import com.habsida.moragoproject.model.payload.PasswordResetPayload;
import com.habsida.moragoproject.repository.PasswordResetRepository;
import com.habsida.moragoproject.security.PasswordResetTokenGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    private final UserService userService;
    private final PasswordResetTokenGenerator passwordResetTokenGenerator;

    public PasswordResetService(PasswordResetRepository passwordResetRepository, UserService userService, PasswordResetTokenGenerator passwordResetTokenGenerator) {
        this.passwordResetRepository = passwordResetRepository;
        this.userService = userService;
        this.passwordResetTokenGenerator = passwordResetTokenGenerator;
    }

    public List<PasswordReset> findAll(){
        return passwordResetRepository.findAll();
    }

    public Page<PasswordReset> findAllPaged(PageRequest pageRequest) {
        return passwordResetRepository.findAll(pageRequest);
    }

    public PasswordReset findById(Long id){
        return passwordResetRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("PasswordReset wasn't found by Id: " + id));
    }

    public Boolean delete(Long id){
        passwordResetRepository.deleteById(id);
        return true;
    }


    public PasswordResetPayload requestPasswordReset(String phone) {

        String userPhone = phone.replaceAll("[^0-9]", "");

        User user = userService.findByPhone(userPhone);

        PasswordReset passwordReset = new PasswordReset();

        PasswordResetPayload resetRequestPayload = new PasswordResetPayload();
        Random random = new Random();

        passwordReset.setUser(user);
        passwordReset.setResetCode(10000 + random.nextInt(90000));
        passwordReset.setPhone(userPhone);
        passwordReset = passwordResetRepository.save(passwordReset);

        resetRequestPayload.setPasswordResetId(passwordReset.getId());
        resetRequestPayload.setExpiration(LocalDateTime.now().plusMinutes(10));
        resetRequestPayload.setHashCode(String.valueOf(
                (userPhone + passwordReset.getResetCode()
                + resetRequestPayload.getExpiration().toString())
                .hashCode()));

        return resetRequestPayload;
    }

    public String validateVerificationCode (PasswordResetInput passwordResetInput) {
        LocalDateTime currentTime = LocalDateTime.now();

        if (currentTime.isAfter(passwordResetInput.getExpiration())) {
            throw new InvalidInputException("Verification code was expired");
        }

        PasswordReset passwordReset = findById(passwordResetInput.getPasswordResetId());

        String expectedHashcode = String.valueOf(
                (passwordReset.getPhone()
                + passwordReset.getResetCode()
                + passwordResetInput.getExpiration().toString())
                .hashCode());

        if (expectedHashcode.equals(passwordResetInput.getHashCode()) ) {
            String token = passwordResetTokenGenerator.generatePasswordResetToken(passwordReset.getPhone());
            passwordReset.setToken(token);
            passwordResetRepository.save(passwordReset);
            return token;
        } else {
            throw new InvalidInputException("Invalid verification code");
        }
    }
}
