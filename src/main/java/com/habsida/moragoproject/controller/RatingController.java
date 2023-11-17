package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Rating;
import com.habsida.moragoproject.model.input.RatingInput;
import com.habsida.moragoproject.service.RatingService;
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
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @QueryMapping
    public List<Rating> getAllRating(){
        return ratingService.findAll();
    }

    @QueryMapping
    public Page<Rating> getAllRatingPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ratingService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Rating getRatingById(@Argument Long id){
        return ratingService.findById(id);
    }

    @MutationMapping
    public Rating createRating(@Argument RatingInput ratingInput){
        return ratingService.create(ratingInput);
    }

    @MutationMapping
    public Boolean deleteRatingById(@Argument Long id){
        return ratingService.delete(id);
    }

    @MutationMapping
    public Rating updateRating(@Argument Long id, @Argument RatingInput ratingInput){
        return ratingService.update(id, ratingInput);
    }
}
