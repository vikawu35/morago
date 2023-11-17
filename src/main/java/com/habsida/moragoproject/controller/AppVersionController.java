package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.AppVersion;
import com.habsida.moragoproject.model.enums.EPlatform;
import com.habsida.moragoproject.model.input.AppVersionInput;
import com.habsida.moragoproject.service.AppVersionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AppVersionController {

    private final AppVersionService appVersionService;


    public AppVersionController(AppVersionService appVersionService) {
        this.appVersionService = appVersionService;
    }

    @QueryMapping
    public List<AppVersion> getAllAppVersion(){
        return appVersionService.findAll();
    }

    @QueryMapping
    public Page<AppVersion> getAllAppVersionPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return appVersionService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public AppVersion getAppVersionByEPlatform(EPlatform ePlatform){
        return appVersionService.findByEPlatform(ePlatform);
    }

    @MutationMapping
    public AppVersion createAppVersion(@Argument AppVersionInput appVersionInput){
        return appVersionService.create(appVersionInput);
    }

    @MutationMapping
    public Boolean deleteAppVersionById(EPlatform ePlatform){
        return appVersionService.delete(ePlatform);
    }

    @MutationMapping
    public AppVersion updateAppVersion(AppVersionInput appVersionInput){
        return appVersionService.update(appVersionInput);
    }
}
