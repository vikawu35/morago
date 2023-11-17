package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Call;
import com.habsida.moragoproject.model.enums.CallStatus;
import com.habsida.moragoproject.model.input.CallInput;
import com.habsida.moragoproject.repository.CallRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class CallService {

    private final CallRepository callRepository;

    public CallService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public List<Call> findAll(){
        return callRepository.findAll();
    }

    public Page<Call> findAllPaged(PageRequest pageRequest) {
        return callRepository.findAll(pageRequest);
    }

    public Call findById(Long id){
        return callRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Call is not found by Id: " + id));
    }

    public Call create(CallInput callInput){
        Call call = new Call();

        if (!isNull(callInput.getCallStatus()) && callInput.getCallStatus().isEmpty()){
            call.setCallStatus(CallStatus.valueOf(callInput.getCallStatus()));
        } else {
            call.setCallStatus(CallStatus.OK);
        }
        if (!isNull(callInput.getChannelName()) && callInput.getChannelName().isEmpty()){
            call.setChannelName(callInput.getChannelName());
        } else {
            call.setChannelName("");
        }
        if (!isNull(callInput.getCommission())){
            call.setCommission(callInput.getCommission());
        } else {
            call.setCommission(0d);
        }
        if (!isNull(callInput.getSum())){
            call.setSum(callInput.getSum());
        } else {
            call.setSum(0d);
        }
        if (!isNull(callInput.getDuration())){
            call.setDuration(callInput.getDuration());
        } else {
            call.setDuration(0);
        }
        if (!isNull(callInput.getIsEndCall())){
            call.setIsEndCall(callInput.getIsEndCall());
        } else {
            call.setIsEndCall(false);
        }
        if (!isNull(callInput.getStatus())){
            call.setStatus(callInput.getStatus());
        } else {
            call.setStatus(false);
        }
        if (!isNull(callInput.getTranslatorHasRated())){
            call.setTranslatorHasRated(callInput.getTranslatorHasRated());
        } else {
            call.setTranslatorHasRated(false);
        }
        if (!isNull(callInput.getUserHasRated())){
            call.setUserHasRated(callInput.getUserHasRated());
        } else {
            call.setUserHasRated(false);
        }
        if (!isNull(callInput.getCaller())){
            call.setCaller(callInput.getCaller());
        }
        if (!isNull(callInput.getRecipient())){
            call.setRecipient(callInput.getRecipient());
        }
        if (!isNull(callInput.getTheme())){
            call.setTheme(callInput.getTheme());
        }
        return callRepository.save(call);
    }

    public Boolean delete(Long id) {
        callRepository.deleteById(id);
        return true;
    }

    public Call update(Long id, CallInput callInput){
        Call call = findById(id);

        if (callInput.getCallStatus() != null
            && !callInput.getCallStatus().isEmpty()) {
            call.setCallStatus(CallStatus.valueOf(callInput.getCallStatus()));
        }

        if (callInput.getChannelName() != null && !callInput.getChannelName().isEmpty()) {
            call.setChannelName(callInput.getChannelName());
        }

        if (callInput.getCommission() != null) {
            call.setCommission(callInput.getCommission());
        }

        if (callInput.getSum() != null) {
            call.setSum(callInput.getSum());
        }

        if (callInput.getDuration() != null) {
            call.setDuration(callInput.getDuration());
        }

        if (callInput.getIsEndCall() != null) {
            call.setIsEndCall(callInput.getIsEndCall());
        }

        if (callInput.getStatus() != null) {
            call.setStatus(callInput.getStatus());
        }

        if (callInput.getTranslatorHasRated() != null) {
            call.setTranslatorHasRated(callInput.getTranslatorHasRated());
        }

        if (callInput.getUserHasRated() != null) {
            call.setUserHasRated(callInput.getUserHasRated());
        }

        return callRepository.save(call);
    }
}
