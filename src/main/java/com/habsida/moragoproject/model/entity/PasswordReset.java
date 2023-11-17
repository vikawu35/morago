package com.habsida.moragoproject.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "password_reset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PasswordReset extends AbstractAuditable{

    private String phone;
    private Integer resetCode;
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
