package com.habsida.moragoproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@NamedEntityGraph(name = "user-roles-profiles-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "roles"),
                @NamedAttributeNode(value = "userProfile"),
                @NamedAttributeNode(value = "translatorProfile"),
        })
@NamedEntityGraph(name = "user-roles-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "roles")
        })
public class User extends AbstractAuditable{

    private String apnToken;
    private Double balance;
    private String fcmToken;
    private String firstName = "";
    private String lastName = "";
    private Boolean isActive;
    private Boolean isDebtor;
    private Boolean isDeleted;
    private Integer onBoardingStatus;
    private String password;
    @Column(unique = true)
    private String phone;
    private Double ratings;
    private Integer totalRatings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasswordReset> passwordResets;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id")
    )
    private List<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TranslatorProfile translatorProfile;

}
