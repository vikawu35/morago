package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Rating;
import com.habsida.moragoproject.model.input.RatingInput;
import com.habsida.moragoproject.repository.RatingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserService userService;

    public RatingService(RatingRepository ratingRepository, UserService userService) {
        this.ratingRepository = ratingRepository;
        this.userService = userService;
    }

    public List<Rating> findAll(){
        return ratingRepository.findAll();
    }

    public Page<Rating> findAllPaged(PageRequest pageRequest) {
        return ratingRepository.findAll(pageRequest);
    }

    public Rating findById(Long id){
        return ratingRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find Rating by Id: " + id));
    }

    public Rating create(RatingInput ratingInput){
        Rating rating = new Rating();

        if(!isNull(ratingInput.getGrade())){
            rating.setGrade(ratingInput.getGrade());
        }else{
            rating.setGrade(0d);
        }
        if(!isNull(ratingInput.getUserGivesRating())){
            rating.setUserGivesRating(userService.findById(ratingInput.getUserGivesRating()));
        }
        if(!isNull(ratingInput.getUserTakesRating())){
            rating.setUserTakesRating(userService.findById(ratingInput.getUserTakesRating()));
        }
        if(!isNull(ratingInput.getUser())){
            rating.setUser(userService.findById(ratingInput.getUser()));
        }
        return ratingRepository.save(rating);
    }

    public Boolean delete(Long id){
        ratingRepository.deleteById(id);
        return true;
    }

    public Rating update(Long id, RatingInput ratingInput){
        Rating rating = findById(id);

        if (ratingInput.getGrade() != null) {
            rating.setGrade(ratingInput.getGrade());
        }

        return ratingRepository.save(rating);
    }
}
