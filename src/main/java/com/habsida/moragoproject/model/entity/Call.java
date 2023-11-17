package com.habsida.moragoproject.model.entity;

import com.habsida.moragoproject.model.enums.CallStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "calls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Call extends AbstractAuditable{


    private CallStatus callStatus;
    private String channelName;
    private Double sum;
    private Double commission;
    private Integer duration;
    private Boolean isEndCall;
    private Boolean status;
    private Boolean translatorHasRated;
    private Boolean userHasRated;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caller_id")
    private User caller;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id")
    private User recipient;



}
