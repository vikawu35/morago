package com.habsida.moragoproject.model.entity;

import com.habsida.moragoproject.model.enums.EStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "withdrawals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Withdrawal extends AbstractAuditable{

    @Enumerated(EnumType.ORDINAL)
    private EStatus status;

    private String accountHolder;
    private String accountNumber;
    private String nameOfBank;
    private Double sum;

    @ManyToOne (cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
