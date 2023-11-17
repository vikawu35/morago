package com.habsida.moragoproject.service;


import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.FrequentlyAskedQuestion;
import com.habsida.moragoproject.model.enums.FAQCategory;
import com.habsida.moragoproject.model.input.FrequentlyAskedQuestionInput;
import com.habsida.moragoproject.repository.FrequentlyAskedQuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class FrequentlyAskedQuestionService {

    private final FrequentlyAskedQuestionRepository frequentlyAskedQuestionRepository;

    public FrequentlyAskedQuestionService(FrequentlyAskedQuestionRepository frequentlyAskedQuestionRepository) {
        this.frequentlyAskedQuestionRepository = frequentlyAskedQuestionRepository;
    }

    public List<FrequentlyAskedQuestion> findAll(){
        return frequentlyAskedQuestionRepository.findAll();
    }

    public Page<FrequentlyAskedQuestion> findAllPaged(PageRequest pageRequest) {
        return frequentlyAskedQuestionRepository.findAll(pageRequest);
    }

    public FrequentlyAskedQuestion findById(Long id){
        return frequentlyAskedQuestionRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find FrequentlyAskedQuestion by Id: " + id));
    }

    public FrequentlyAskedQuestion create(FrequentlyAskedQuestionInput frequentlyAskedQuestionInput){
        FrequentlyAskedQuestion frequentlyAskedQuestion = new FrequentlyAskedQuestion();

        if (!isNull(frequentlyAskedQuestionInput.getQuestion()) && !frequentlyAskedQuestionInput.getQuestion().isEmpty()){
            frequentlyAskedQuestion.setQuestion(frequentlyAskedQuestionInput.getQuestion());
        } else {
            frequentlyAskedQuestion.setQuestion("");
        }

        if (!isNull(frequentlyAskedQuestionInput.getAnswer()) && !frequentlyAskedQuestionInput.getAnswer().isEmpty()){
            frequentlyAskedQuestion.setAnswer(frequentlyAskedQuestionInput.getAnswer());
        } else {
            frequentlyAskedQuestion.setQuestion("");
        }

        if (!isNull(frequentlyAskedQuestionInput.getCategory()) && !frequentlyAskedQuestionInput.getCategory().isEmpty()){
            frequentlyAskedQuestion.setCategory(FAQCategory.valueOf(frequentlyAskedQuestionInput.getCategory()));
        } else {
            frequentlyAskedQuestion.setCategory(FAQCategory.FAQ1);
        }

        return frequentlyAskedQuestionRepository.save(frequentlyAskedQuestion);
    }

    public Boolean delete(Long id){
        frequentlyAskedQuestionRepository.deleteById(id);
        return true;
    }

    public FrequentlyAskedQuestion update(Long id, FrequentlyAskedQuestionInput frequentlyAskedQuestionInput){
        FrequentlyAskedQuestion frequentlyAskedQuestion = findById(id);

        if (frequentlyAskedQuestionInput.getQuestion() != null && !frequentlyAskedQuestionInput.getQuestion().isEmpty()) {
            frequentlyAskedQuestion.setQuestion(frequentlyAskedQuestionInput.getQuestion());
        }

        if (frequentlyAskedQuestionInput.getAnswer() != null && !frequentlyAskedQuestionInput.getAnswer().isEmpty()) {
            frequentlyAskedQuestion.setAnswer(frequentlyAskedQuestionInput.getAnswer());
        }

        if (frequentlyAskedQuestionInput.getCategory() != null && !frequentlyAskedQuestionInput.getCategory().isEmpty()) {
            frequentlyAskedQuestion.setCategory(FAQCategory.valueOf(frequentlyAskedQuestionInput.getCategory()));
        }

        return frequentlyAskedQuestionRepository.save(frequentlyAskedQuestion);
    }
}
