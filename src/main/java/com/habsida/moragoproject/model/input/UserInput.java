package com.habsida.moragoproject.model.input;

import com.habsida.moragoproject.model.entity.Role;
import com.habsida.moragoproject.model.entity.TranslatorProfile;
import com.habsida.moragoproject.model.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInput {

    private String apnToken;
    private String fcmToken;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;

    private List<Role> roles;
    private UserProfile userProfile ;
    private TranslatorProfile translatorProfile;
}
