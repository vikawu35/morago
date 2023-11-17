package com.habsida.moragoproject.service;

import com.habsida.moragoproject.exception.ResourceNotFoundException;
import com.habsida.moragoproject.model.entity.Notification;
import com.habsida.moragoproject.model.input.NotificationInput;
import com.habsida.moragoproject.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationService(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }

    public Page<Notification> findAllPaged(PageRequest pageRequest) {
        return notificationRepository.findAll(pageRequest);
    }

    public Notification findById(Long id){
        return notificationRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Cannot find Notification by Id: " + id));
    }

    public Notification create(NotificationInput notificationInput){
        Notification notification = new Notification();

        if(!isNull(notificationInput.getText()) && !notificationInput.getText().isEmpty()){
            notification.setText(notificationInput.getText());
        }else{
            notification.setText("");
        }
        if(!isNull(notificationInput.getTitle()) && !notificationInput.getTitle().isEmpty()){
            notification.setTitle(notificationInput.getTitle());
        }else{
            notification.setTitle("");
        }
        if(!isNull(notificationInput.getUser())){
            notification.setUser(userService.findById(notificationInput.getUser()));
        }

        return notificationRepository.save(notification);
    }

    public Boolean delete(Long id){
        notificationRepository.deleteById(id);
        return true;
    }

    public Notification update(Long id, NotificationInput notificationInput){
        Notification notification = findById(id);

        if (notificationInput.getText() != null && !notificationInput.getText().isEmpty()) {
            notification.setText(notificationInput.getText());
        }

        if (notificationInput.getTitle() != null && !notificationInput.getTitle().isEmpty()) {
            notification.setTitle(notificationInput.getTitle());
        }

        return notificationRepository.save(notification);
    }
}
