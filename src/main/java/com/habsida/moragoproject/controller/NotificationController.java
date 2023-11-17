package com.habsida.moragoproject.controller;

import com.habsida.moragoproject.model.entity.Notification;
import com.habsida.moragoproject.model.input.NotificationInput;
import com.habsida.moragoproject.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @QueryMapping
    public List<Notification> getAllNotification(){
        return notificationService.findAll();
    }

    @QueryMapping
    public Page<Notification> getAllNotificationPaged(@Argument int page, @Argument int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return notificationService.findAllPaged(pageRequest);
    }

    @QueryMapping
    public Notification getNotificationById(@Argument Long id){
        return notificationService.findById(id);
    }

    @MutationMapping
    public Notification createNotification(@Argument NotificationInput notificationInput){
        return notificationService.create(notificationInput);
    }

    @MutationMapping
    public Boolean deleteNotificationById(@Argument Long id){
        return notificationService.delete(id);
    }

    @MutationMapping
    public Notification updateNotification(@Argument Long id, @Argument NotificationInput notificationInput){
        return notificationService.update(id, notificationInput);
    }
}
