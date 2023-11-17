package com.habsida.moragoproject.model.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PasswordResetPayload {

    private Long passwordResetId;
    private String hashCode;
    private LocalDateTime expiration;

}
