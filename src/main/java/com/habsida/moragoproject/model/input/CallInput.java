package com.habsida.moragoproject.model.input;

import com.habsida.moragoproject.model.entity.Theme;
import com.habsida.moragoproject.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CallInput {

    private String callStatus;
    private String channelName;
    private Double commission;
    private Integer duration;
    private Boolean isEndCall;
    private Boolean status;
    private Double sum;
    private Boolean translatorHasRated;
    private Boolean userHasRated;

    private User caller;
    private User recipient;
    private Theme theme;
}
