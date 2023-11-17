package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.InvalidInputException;
import com.habsida.moragoproject.exception.JwtTokenException;
import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Role;
import com.habsida.moragoproject.model.entity.User;
import com.habsida.moragoproject.model.input.PasswordUpdateInput;
import com.habsida.moragoproject.model.input.UserInput;
import com.habsida.moragoproject.model.input.UsernameUpdateInput;
import com.habsida.moragoproject.model.payload.Profile;
import com.habsida.moragoproject.repository.UserRepository;
import com.habsida.moragoproject.security.PasswordResetTokenGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final PasswordResetTokenGenerator passwordResetTokenGenerator;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, PasswordResetTokenGenerator passwordResetTokenGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.passwordResetTokenGenerator = passwordResetTokenGenerator;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Page<User> findAllPaged(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("User was not found by Id: " + id));
    }

    public User findByPhone(String phone){
        return userRepository.findByPhone(phone)
                 .orElseThrow(()->new ResourceNotFoundException("User was not found by phone: " + phone));
    }
    public Boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public User create(UserInput userInput) {
        User user = new User();

        user.setPhone(userInput.getPhone());
        user.setPassword(passwordEncoder.encode(userInput.getPassword()));

        if (userInput.getFirstName() != null && !userInput.getFirstName().isEmpty()) {
            user.setFirstName(userInput.getFirstName());
        }

        if (userInput.getLastName() != null && !userInput.getLastName().isEmpty()) {
            user.setLastName(userInput.getLastName());
        }

        user.setIsDeleted(false);
        user.setBalance(0.0);
        user.setIsActive(true);
        user.setIsDebtor(false);
        user.setOnBoardingStatus(0);
        user.setRatings(0.0);
        user.setApnToken("");
        user.setFcmToken("");
        user.setTotalRatings(0);

        List<Role> userRoles = new ArrayList<>();

        for (Role role : userInput.getRoles()) {
            Role createdRole = roleService.create(role);
            userRoles.add(createdRole);
        }
        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    public Boolean delete (Long id ){
        userRepository.deleteById(id);
        return true;
    }

    public User update(Long id, UserInput userInput){
        User user = findById(id);

        if (userInput.getFirstName() != null && !userInput.getFirstName().isEmpty()) {
            user.setFirstName(userInput.getFirstName());
        }

        if (userInput.getLastName() != null && !userInput.getLastName().isEmpty()) {
            user.setLastName(userInput.getLastName());
        }

        if (userInput.getApnToken() != null && !userInput.getApnToken().isEmpty()) {
            user.setApnToken(userInput.getApnToken());
        }

        if (userInput.getFcmToken() != null && !userInput.getFcmToken().isEmpty()) {
            user.setFcmToken(userInput.getFcmToken());
        }

        return userRepository.save(user);
    }

    public Profile getProfile(User user) {
        Profile profile = new Profile();

        if(user.getUserProfile() != null) {
            profile.setIsFreeCallMade(user.getUserProfile().getIsFreeCallMade());
            profile.setSelfDescription("USER");
        }
        if (user.getTranslatorProfile() != null) {
            profile.setEmail(user.getTranslatorProfile().getEmail());
            profile.setLanguages(user.getTranslatorProfile().getLanguages());
            profile.setThemes(user.getTranslatorProfile().getThemes());
            profile.setDateOfBirth(user.getTranslatorProfile().getDateOfBirth());
            profile.setIsOnline(user.getTranslatorProfile().getIsOnline());
            profile.setIsAvailable(user.getTranslatorProfile().getIsAvailable());
            profile.setLevelOfKorean(user.getTranslatorProfile().getLevelOfKorean());
            profile.setSelfDescription("TRANSLATOR");
        }
        return profile;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByPhone(authentication.getName()).get();
    }

    public Boolean deleteCurrentUser() {
        User user = getCurrentUser();
        user.setIsActive(false);
        user.setIsDeleted(true);
        userRepository.save(user);

        return true;
    }

    public User updatePassword(PasswordUpdateInput passwordUpdateInput) {

        if (!passwordResetTokenGenerator.validToken(passwordUpdateInput.getToken())) {
            throw new JwtTokenException("Invalid password reset token");
        }
        if (!passwordResetTokenGenerator.checkExpiration(passwordUpdateInput.getToken())) {
            throw new JwtTokenException("Password reset token was expired");
        }

        User user = findByPhone(passwordResetTokenGenerator.getUsernameFromToken(passwordUpdateInput.getToken()));
        user.setPassword(passwordEncoder.encode(passwordUpdateInput.getNewPassword()));
        return userRepository.save(user);
    }

    public String updateUsername(UsernameUpdateInput usernameUpdateInput) {
        User user = getCurrentUser();

        if (!passwordEncoder.matches(usernameUpdateInput.getPassword(), user.getPassword())) {
            throw new InvalidInputException("Wrong password");
        }

        String cleanedPhone = usernameUpdateInput.getNewPhone().replaceAll("[^0-9]", "");
        user.setPhone(cleanedPhone);
        userRepository.save(user);

        return "Username was updated";
    }
}
