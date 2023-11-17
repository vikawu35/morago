package com.habsida.moragoproject.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "debtors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Debtor extends AbstractAuditable{

    private String accountHolder;
    private Boolean isPaid;
    private String nameOfBank;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
