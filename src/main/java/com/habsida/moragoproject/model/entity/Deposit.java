package com.habsida.moragoproject.model.entity;

import com.habsida.moragoproject.model.enums.EStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "deposits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Deposit extends AbstractAuditable{

    private String accountHolder;
    private String nameOfBank;
    private Double coin;
    private Double won;
    private EStatus status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
