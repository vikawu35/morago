package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.FrequentlyAskedQuestion;
import com.habsida.moragoproject.model.input.FrequentlyAskedQuestionInput;
import com.habsida.moragoproject.service.FrequentlyAskedQuestionService;
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
public class FrequentlyAskedQuestionController {
    private final FrequentlyAskedQuestionService frequentlyAskedQuestionService;

    public FrequentlyAskedQuestionController(FrequentlyAskedQuestionService frequentlyAskedQuestionService) {
        this.frequentlyAskedQuestionService = frequentlyAskedQuestionService;
    }

    @QueryMapping
    public List<FrequentlyAskedQuestion> getAllFrequentlyAskedQuestion(){
        return frequentlyAskedQuestionService.findAll();
    }

    @QueryMapping
    public Page<FrequentlyAskedQuestion> getAllFrequentlyAskedQuestionPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return frequentlyAskedQuestionService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public FrequentlyAskedQuestion getFrequentlyAskedQuestionById(@Argument Long id){
        return frequentlyAskedQuestionService.findById(id);
    }

    @MutationMapping
    public FrequentlyAskedQuestion createFrequentlyAskedQuestion(@Argument FrequentlyAskedQuestionInput frequentlyAskedQuestionInput){
        return frequentlyAskedQuestionService.create(frequentlyAskedQuestionInput);
    }

    @MutationMapping
    public Boolean deleteFrequentlyAskedQuestionById(@Argument Long id){
        return frequentlyAskedQuestionService.delete(id);
    }

    @MutationMapping
    public FrequentlyAskedQuestion updateFrequentlyAskedQuestion(@Argument Long id, @Argument FrequentlyAskedQuestionInput frequentlyAskedQuestionInput){
        return frequentlyAskedQuestionService.update(id, frequentlyAskedQuestionInput);
    }
}
