package com.habsida.moragoproject.model.entity;

import com.habsida.moragoproject.model.enums.FAQCategory;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "faqs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FrequentlyAskedQuestion extends AbstractAuditable{


    private FAQCategory category;
    private String answer;
    private String question;


}
