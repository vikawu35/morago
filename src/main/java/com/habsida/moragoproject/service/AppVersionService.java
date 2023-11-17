package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.AppVersion;
import com.habsida.moragoproject.model.enums.EPlatform;
import com.habsida.moragoproject.model.input.AppVersionInput;
import com.habsida.moragoproject.repository.AppVersionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class AppVersionService {

    private final AppVersionRepository appVersionRepository;

    public AppVersionService(AppVersionRepository appVersionRepository) {
        this.appVersionRepository = appVersionRepository;
    }

    public List<AppVersion> findAll(){
        return appVersionRepository.findAll();
    }

    public Page<AppVersion> findAllPaged(PageRequest pageRequest) {
        return appVersionRepository.findAll(pageRequest);
    }

    public AppVersion findByEPlatform(EPlatform ePlatform){
        return appVersionRepository.findByPlatform(ePlatform)
                .orElseThrow(()->new ResourceNotFoundException("AppVersion is not found by Platform: " + ePlatform));
    }

    public AppVersion create(AppVersionInput appVersionInput){
        AppVersion appVersion = new AppVersion();

        if (!isNull(appVersionInput.getPlatform()) && !appVersionInput.getPlatform().isEmpty()){
            appVersion.setPlatform(EPlatform.valueOf(appVersionInput.getPlatform()));
        } else {
            appVersion.setPlatform(EPlatform.valueOf(""));
        }
        if (!isNull(appVersionInput.getLatest()) && !appVersionInput.getLatest().isEmpty()){
            appVersion.setLatest(appVersionInput.getLatest());
        } else {
            appVersion.setLatest("");
        }
        if (!isNull(appVersionInput.getMin()) && !appVersionInput.getMin().isEmpty()){
            appVersion.setMin(appVersionInput.getMin());
        } else {
            appVersion.setMin("");
        }
        return appVersionRepository.save(appVersion);
    }

    public Boolean delete(EPlatform ePlatform){
        appVersionRepository.deleteByPlatform(ePlatform);
        return true;
    }

    public AppVersion update(AppVersionInput appVersionInput) {
        AppVersion appVersion = appVersionRepository.findByPlatform(EPlatform.valueOf(appVersionInput.getPlatform())).get();

        if (appVersionInput.getPlatform() != null && !appVersionInput.getPlatform().isEmpty()) {
            appVersion.setPlatform(EPlatform.valueOf(appVersionInput.getPlatform()));
        }
        if (appVersionInput.getLatest() != null && !appVersionInput.getLatest().isEmpty()) {
            appVersion.setPlatform(EPlatform.valueOf(appVersionInput.getLatest()));
        }
        if (appVersionInput.getMin() != null && !appVersionInput.getMin().isEmpty()) {
            appVersion.setMin(appVersionInput.getMin());
        }
        return appVersionRepository.save(appVersion);
    }
}
