package com.habsida.moragoproject.model.entity;

import com.habsida.moragoproject.model.enums.EPlatform;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_version")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppVersion {

    @Id
    private EPlatform platform;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    private String latest;
    private String min;
}
