package com.habsida.moragoproject.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "coins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Coin extends AbstractAuditable{

    private Double coin;
    private Double won;
}
