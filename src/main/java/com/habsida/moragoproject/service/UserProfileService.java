package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.UserProfile;
import com.habsida.moragoproject.model.input.UserProfileInput;
import com.habsida.moragoproject.repository.UserProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserProfile> findAll(){
        return userProfileRepository.findAll();
    }

    public Page<UserProfile> findAllPaged(PageRequest pageRequest) {
        return userProfileRepository.findAll(pageRequest);
    }

    public UserProfile findById(Long id){
        return userProfileRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find UserProfile by Id: " + id));
    }

    public UserProfile create(){
        UserProfile userProfile = new UserProfile();

        userProfile.setIsFreeCallMade(false);

        return userProfileRepository.save(userProfile);
    }

    public Boolean delete(Long id){
        userProfileRepository.deleteById(id);
        return true;
    }

    public UserProfile update(Long id, UserProfileInput userProfileInput){

        UserProfile userProfile = findById(id);

        if (userProfileInput.getIsFreeCallMade() != null) {
            userProfile.setIsFreeCallMade(userProfileInput.getIsFreeCallMade());
        }

        return userProfileRepository.save(userProfile);
    }
}
